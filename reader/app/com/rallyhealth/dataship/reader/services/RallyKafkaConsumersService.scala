package com.rallyhealth.dataship.reader.services

import akka.actor.Scheduler
import com.rallyhealth.dataship.database.services.{DatashipDatabaseHmacService, DatashipDatabaseNeoEncryptionService}
import com.rallyhealth.dataship.reader.configs.DatashipReaderConfig
import com.rallyhealth.spartan.v2.util.logging.DefaultLogger

import scala.concurrent.ExecutionContext

/**
  * [[RallyKafkaConsumersService]] holds the group of [[KafkaReader]] and will initialize the readers.
  *
  */
trait RallyKafkaConsumersService {
  def initialize(
    dataLocalizationService: DataLocalizationService
  )(
    implicit
    datashipNeoEncryptionService: DatashipDatabaseNeoEncryptionService,
    datashipHmacService: DatashipDatabaseHmacService
  ): Unit

  def numReaders: Int
}

class RallyKafkaConsumersServiceImpl(
  datashipReaderConfig: DatashipReaderConfig,
  kafkaConsumerScheduler: Scheduler,
  encryptedEnvelopeDecrypterService: EncryptedMessageDecrypterService
)(implicit ec: ExecutionContext)
  extends RallyKafkaConsumersService with DefaultLogger {

  override val numReaders: Int = datashipReaderConfig.numKafkaReaders

  private val readers: List[KafkaReader] = List.fill(numReaders)(
    new KafkaReaderImpl(datashipReaderConfig, kafkaConsumerScheduler, encryptedEnvelopeDecrypterService, None))

  override def initialize(dataLocalizationService: DataLocalizationService)(
    implicit
    datashipNeoEncryptionService: DatashipDatabaseNeoEncryptionService,
    datashipHmacService: DatashipDatabaseHmacService
  ): Unit = {
    logger.info("initializing all readers")
    for (reader <- readers) {
      reader.initialize(dataLocalizationService)
    }
  }
}
