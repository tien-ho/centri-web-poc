package com.rallyhealth.dataship.reader.services

import java.time.Instant
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import java.util.{Calendar, Collections, Properties}

import akka.actor.Scheduler
import com.optum.exts.eligibility.rally.model.RallyEligibility
import com.optum.exts.rally.eligibility.model.RallyEligibilityKey
import com.rallyhealth.dataship.database.models._
import com.rallyhealth.dataship.database.services.{DatashipDatabaseHmacService, DatashipDatabaseNeoEncryptionService}
import com.rallyhealth.dataship.reader.configs.DatashipReaderConfig
import com.rallyhealth.spartan.v2.util.logging.DefaultLogger
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient
import io.confluent.kafka.serializers.{KafkaAvroDeserializer, KafkaAvroDeserializerConfig}
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.Deserializer

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer
import scala.concurrent.duration.{FiniteDuration, _}
import scala.concurrent.{Await, ExecutionContext}
import scala.util.control.Breaks.breakable

trait KafkaReader {
  def initialize(
    dataLocalizationService: DataLocalizationService
  )(
    implicit
    datashipNeoEncryptionService: DatashipDatabaseNeoEncryptionService,
    datashipHmacService: DatashipDatabaseHmacService
  ): Unit
}

/**
  * Kafka reader for configurable number of consumers. will read at regular intervals
  *
  */
class KafkaReaderImpl(
  config: DatashipReaderConfig,
  scheduler: Scheduler,
  encryptedEnvelopeDecrypterService: EncryptedMessageDecrypterService,
  defaultKafkaConsumer: Option[KafkaConsumer[RallyEligibilityWrapper[RallyEligibilityKey], RallyEligibilityWrapper[RallyEligibility]]]
)(implicit ec: ExecutionContext) extends KafkaReader with DefaultLogger {

  private val properties = getProperties(config)
  private val topic = config.kafkaTopic
  private val pollPeriod = config.kafkaPollPeriod

  // leaving key deserializer generic until we actually need something from the key
  private val keyKafkaAvroDeserializer = new KafkaAvroDeserializer
  keyKafkaAvroDeserializer.configure(mapAsJavaMap(properties), true)

  // value deserializer needs to be customized to decrypt and unwrap the inner avro message
  private val valueKafkaAvroDeserializer = new KafkaAvroDeserializer()
  valueKafkaAvroDeserializer.configure(mapAsJavaMap(properties), false)
  private val valueDeserializer: Deserializer[RallyEligibilityWrapper[RallyEligibility]] =
    new RallyKafkaAvroDeserializer[RallyEligibility](
      config.kafkaEncryptedMessagesFlag, valueKafkaAvroDeserializer, encryptedEnvelopeDecrypterService)

  private val reader = defaultKafkaConsumer.getOrElse(
    new KafkaConsumer[Object, RallyEligibilityWrapper[RallyEligibility]](properties, keyKafkaAvroDeserializer, valueDeserializer))
  private val registryClient: CachedSchemaRegistryClient =
    new CachedSchemaRegistryClient(config.kafkaSchemaRepo, 100)

  def initialize(
    dataLocalizationService: DataLocalizationService
  )(
    implicit
    datashipNeoEncryptionService: DatashipDatabaseNeoEncryptionService,
    datashipHmacService: DatashipDatabaseHmacService
  ): Unit = {
    val kafkaReadFrequency: Int = config.kafkaReadFrequency
    val kafkaReadDurationInMilli: FiniteDuration = Duration.create(kafkaReadFrequency, TimeUnit.MILLISECONDS)

    reader.subscribe(Collections.singletonList(topic))
    scheduler.schedule(0.millis, kafkaReadDurationInMilli) {
      read(dataLocalizationService)
    }
  }

  private def read(
    dataLocalizationService: DataLocalizationService
  )(
    implicit
    datashipNeoEncryptionService: DatashipDatabaseNeoEncryptionService,
    datashipHmacService: DatashipDatabaseHmacService
  ): Unit = {

    val start = Calendar.getInstance().getTimeInMillis
    val counter = new AtomicInteger(0)
    var success = false

    breakable {
      val records = reader.poll(pollPeriod)
      if (!records.isEmpty) {
        // Printing out the value for now until we know what to do to each record that comes in
        logger.info(s"processing ${records.size} records")

        val dataEntities: ListBuffer[IndividualDataEntity] = ListBuffer()
        val lookupEntities: ListBuffer[IndividualLookupEntity] = ListBuffer()

        for (record <- records) {
          val eligibility = record.value
          val dataEntity = DatashipAvroRecordHelper.buildIndividualDataEntity(
            eligibility.value,
            eligibility.rawAvroBytes,
            "someSchemaName",
            "someSchemaVersion"
          )

          val lookupEntity = DatashipAvroRecordHelper.buildIndividualLookupEntity(eligibility.value)

          // Add to the lists only when the JSON was validated against both lookup and data entities.
          // Otherwise, the lists would have data entities that do not correspond to any lookup entities,
          // or, vice versa.
          dataEntity.map(dataEntities += _)
          lookupEntity.map(lookupEntities += _)
        }

        val startTime = Instant.now().toEpochMilli
        if (config.dataLocalizationEnabled) {
          // Intentionally executing DB operations synchronously here to process messages in order
          Await.result(
            dataLocalizationService.insertBatch(lookupEntities, dataEntities),
            config.dataLocalizationMaxWaitTimeSec.seconds
          ) match {
              case DatabaseSuccessResponse() =>
                val duration = Instant.now().toEpochMilli - startTime
                logger.info(s"Successfully completed DB operations for individual data with size=[${dataEntities.size}] duration=[${duration}]")
                success = true

              case DatabaseErrorResponse(message) =>
                logger.error(s"Failed to execute DB operations for individual data with size=[${dataEntities.size}] message=${message}")
            }
        } else {
          // If the data localization is not enabled, we have nothing more to do.
          // We simply mark it as success so that the reader can commit the offsets.
          success = true
        }

        // Commit the offsets if all records have been processed successfully
        if (success)
          reader.commitSync()

        // add to the DB record by record or queue it up somehow into a list or batch?
        counter.addAndGet(records.size)

        val end = Calendar.getInstance().getTimeInMillis()
        val duration = end - start
        logger.info("Read " + counter.intValue + " messages in " + duration + "ms")
      }
    }
  }

  private def getProperties(config: DatashipReaderConfig) = {
    val consumerProps = new Properties()

    consumerProps.put("auto.offset.reset", config.kafkaAutoOffsetReset)
    consumerProps.put("bootstrap.servers", config.kafkaBroker)
    consumerProps.put("enable.auto.commit", "false")
    consumerProps.put("group.id", config.kafkaGroupId)
    consumerProps.put("max.poll.records", config.kafkaMaxPollRecords)
    consumerProps.put("schema.registry.url", config.kafkaSchemaRepo)
    consumerProps.put("security.protocol", config.kafkaSecurityProtocol)
    consumerProps.put("session.timeout.ms", config.kafkaSessionTimeoutMs)
    consumerProps.put("ssl.key.password", config.kafkaSSLKeyPassword)
    consumerProps.put("ssl.keystore.location", config.kafkaSSLKeystoreLocation)
    consumerProps.put("ssl.keystore.password", config.kafkaSSLKeystorePassword)
    consumerProps.put("ssl.truststore.location", config.kafkaSSLTruststoreLocation)
    consumerProps.put("ssl.truststore.password", config.kafkaSSLTruststorePassword)
    consumerProps.put("zookeeper.connect", config.kafkaZookeeper)
    consumerProps.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");
    consumerProps
  }
}

case class RallyEligibilityWrapper[T](val value: T, val rawAvroBytes: Array[Byte])
