package com.rallyhealth.dataship.reader.services

import com.miguno.akka.testing.{MockScheduler, VirtualTime}
import com.optum.exts.eligibility.rally.model._
import com.optum.exts.rally.eligibility.model.RallyEligibilityKey
import com.rallyhealth.dataship.database.models.{DatabaseErrorResponse, DatabaseSuccessResponse}
import com.rallyhealth.dataship.database.services.{DatashipDatabaseHmacService, DatashipDatabaseNeoEncryptionService}
import com.rallyhealth.dataship.reader.configs.DatashipReaderConfig
import com.rallyhealth.dataship.reader.configs.DatashipReaderConfig.Defaults
import com.rallyhealth.illuminati.v9.BaseSecretConfig
import com.rallyhealth.spartan.v2.config.MemoryRallyConfig
import com.rallyhealth.spartan.v2.util.logging.DefaultLogger
import org.apache.kafka.clients.consumer.{ConsumerRecord, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.TopicPartition
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito._
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FunSpec, Matchers}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.language.postfixOps

class RallyKafkaConsumersServiceSpec extends FunSpec
  with MockitoSugar
  with Matchers
  with BeforeAndAfter
  with BeforeAndAfterAll
  with DefaultLogger {

  class Fixture {
    val timer = new VirtualTime
    val mockScheduler: MockScheduler = timer.scheduler
    val rallyConfig = new MemoryRallyConfig(
      Map(
        s"dataship.data.localization.enabled" -> "true",
        s"kafka.key.decryption.location" -> "conf/localhost_pkcs8.pem",
        s"kafka.ssl.keystore.location" -> "conf/localhost_keystore.jks",
        s"kafka.ssl.truststore.location" -> "conf/localhost_truststore.jks"
      )
    )
    val secretConfig: BaseSecretConfig = BaseSecretConfig.fromTemplateFilename(rallyConfig, Defaults.secretTemplatePath)
    val datashipReaderConfig = new DatashipReaderConfig(rallyConfig, secretConfig)

    //    val schemaRegistryClient = mock[CachedSchemaRegistryClient]
    implicit val datashipNeoEncryptionService = DatashipDatabaseNeoEncryptionService(datashipReaderConfig.databaseEncryptionService)
    implicit val datashipHmacService = DatashipDatabaseHmacService(datashipReaderConfig.databaseHmacService)

    val decryptor = mock[EncryptedMessageDecrypterService]
    val dataLocalizationService: DataLocalizationService = mock[DataLocalizationService]
    when(dataLocalizationService.insertBatch(any(), any())).thenReturn(Future.successful(DatabaseSuccessResponse()))

    val consumerRecords = new Answer[ConsumerRecords[RallyEligibilityWrapper[RallyEligibilityKey], RallyEligibilityWrapper[RallyEligibility]]]() {
      override def answer(invocation: InvocationOnMock): ConsumerRecords[RallyEligibilityWrapper[RallyEligibilityKey], RallyEligibilityWrapper[RallyEligibility]] = {
        val validRecord = generateValidAvroRecord()
        generateConsumerRecords(validRecord)
      }
    }
  }

  describe("RallyKafkaConsumersService") {

    describe("should initialize fake readers") {
      it("initialize group of readers") {
        val f = new Fixture
        import f._
        val mockKafkaReader: KafkaReader = mock[KafkaReader]
        val rallyKafkaConsumer = new RallyKafkaConsumersServiceImpl(datashipReaderConfig, mockScheduler, decryptor)
        val wrapper = new RallyEligibilityWrapper[String]("abc", "abc".getBytes)
        //    when(mockKafkaReader.initialize(any())).thenReturn(Future.successful(Unit))
        rallyKafkaConsumer.initialize(dataLocalizationService)
      }
    }

    describe("should read") {
      it("reader should read two") {
        val f = new Fixture
        import f._
        val mockKafkaConsumer = mock[KafkaConsumer[RallyEligibilityWrapper[RallyEligibilityKey], RallyEligibilityWrapper[RallyEligibility]]]
        when(mockKafkaConsumer.poll(datashipReaderConfig.kafkaPollPeriod)).thenAnswer(consumerRecords)

        val rallyKakfaReader = new KafkaReaderImpl(datashipReaderConfig, mockScheduler, decryptor, Some(mockKafkaConsumer))
        when(dataLocalizationService.insertBatch(any(), any())).thenReturn(Future.successful(DatabaseSuccessResponse()))
        rallyKakfaReader.initialize(dataLocalizationService)
        verify(dataLocalizationService, times(0)).insertBatch(any(), any())

        timer.advance(1.millis)

        verify(dataLocalizationService, times(2)).insertBatch(any(), any())

        when(mockKafkaConsumer.poll(datashipReaderConfig.kafkaPollPeriod))
          .thenAnswer(new Answer[ConsumerRecords[RallyEligibilityWrapper[RallyEligibilityKey], RallyEligibilityWrapper[RallyEligibility]]]() {
            override def answer(invocation: InvocationOnMock): ConsumerRecords[RallyEligibilityWrapper[RallyEligibilityKey], RallyEligibilityWrapper[RallyEligibility]] = {
              val recordsMap = new java.util.HashMap[TopicPartition, java.util.List[ConsumerRecord[RallyEligibilityWrapper[RallyEligibilityKey], RallyEligibilityWrapper[RallyEligibility]]]]()
              new ConsumerRecords[RallyEligibilityWrapper[RallyEligibilityKey], RallyEligibilityWrapper[RallyEligibility]](recordsMap)
            }
          })
        timer.advance(1.millis)
      }

      it("should read when data localization is not enabled") {
        val f = new Fixture
        import f._

        val rallyConfigLocalizationDisabled = new MemoryRallyConfig(
          Map(
            s"dataship.data.localization.enabled" -> "false"
          )
        )
        val newDatashipReaderConfig = new DatashipReaderConfig(rallyConfigLocalizationDisabled, secretConfig)

        val mockKafkaConsumer = mock[KafkaConsumer[RallyEligibilityWrapper[RallyEligibilityKey], RallyEligibilityWrapper[RallyEligibility]]]
        when(mockKafkaConsumer.poll(newDatashipReaderConfig.kafkaPollPeriod)).thenAnswer(consumerRecords)
        val rallyKakfaReader = new KafkaReaderImpl(newDatashipReaderConfig, mockScheduler, decryptor, Some(mockKafkaConsumer))
        when(dataLocalizationService.insertBatch(any(), any())).thenReturn(Future.successful(DatabaseSuccessResponse()))
        rallyKakfaReader.initialize(dataLocalizationService)
        verify(dataLocalizationService, times(0)).insertBatch(any(), any())
        timer.advance(1.millis)
        verify(dataLocalizationService, times(0)).insertBatch(any(), any())
      }

      it("should not fail when data localization fails") {
        val f = new Fixture
        import f._

        val mockKafkaConsumer = mock[KafkaConsumer[RallyEligibilityWrapper[RallyEligibilityKey], RallyEligibilityWrapper[RallyEligibility]]]
        when(mockKafkaConsumer.poll(datashipReaderConfig.kafkaPollPeriod)).thenAnswer(consumerRecords)
        val rallyKakfaReader = new KafkaReaderImpl(datashipReaderConfig, mockScheduler, decryptor,
          Some(mockKafkaConsumer))
        when(dataLocalizationService.insertBatch(any(), any())).thenReturn(Future.successful(DatabaseErrorResponse("Injecting database failure here!")))
        rallyKakfaReader.initialize(dataLocalizationService)
        verify(dataLocalizationService, times(0)).insertBatch(any(), any())
        timer.advance(1.millis)
        verify(dataLocalizationService, times(2)).insertBatch(any(), any())
      }
    }

    it("should not fail when Avro message cannot be validated against database models") {
      val f = new Fixture
      import f._

      val invalidConsumerRecords = new Answer[ConsumerRecords[RallyEligibilityWrapper[RallyEligibilityKey], RallyEligibilityWrapper[RallyEligibility]]]() {
        override def answer(invocation: InvocationOnMock): ConsumerRecords[RallyEligibilityWrapper[RallyEligibilityKey], RallyEligibilityWrapper[RallyEligibility]] = {
          generateConsumerRecords(generateInvalidAvroRecord())
        }
      }

      val dataLocalizationServiceMock: DataLocalizationService = mock[DataLocalizationService]
      val mockKafkaConsumer = mock[KafkaConsumer[RallyEligibilityWrapper[RallyEligibilityKey], RallyEligibilityWrapper[RallyEligibility]]]
      when(mockKafkaConsumer.poll(datashipReaderConfig.kafkaPollPeriod)).thenAnswer(invalidConsumerRecords)
      val rallyKakfaReader = new KafkaReaderImpl(datashipReaderConfig, mockScheduler, decryptor, Some(mockKafkaConsumer))
      when(dataLocalizationServiceMock.insertBatch(any(), any())).thenReturn(Future.successful(DatabaseSuccessResponse()))

      rallyKakfaReader.initialize(dataLocalizationServiceMock)
      verify(dataLocalizationServiceMock, times(0)).insertBatch(any(), any())

      timer.advance(1.millis)
      verify(dataLocalizationService, times(0)).insertBatch(any(), any())
    }

  }

  private def generateValidAvroRecord(): RallyEligibility = {
    import RallyKafkaConsumerSpecHelper._

    validAvroRallyEligibility
  }

  private def generateInvalidAvroRecord(): RallyEligibility = {
    val postalAddress: PostalAddress = new PostalAddress(
      street1 = "10 Main St",
      street2 = "Empty",
      city = "Conroe",
      zip = "77303",
      stateCode = "TX",
      countryCode = "USA",
      countrySubCode = "USA"
    )

    val personName = new PersonName(firstName = "John", lastName = "Doe")
    val authoritativeRepresentative = new AuthoritativeRepresentative(
      personName, postalAddress
    )

    val demographics = new Demographics(
      familyID = "abc",
      policyNumber = "123",
      personName = personName,
      gender = "M",
      dateOfBirth = "1983-03-31",
      cdbRelationshipCode = "S",
      cdbRelationshipDescription = "Yes",
      dependentSequenceNumber = "1",
      individualRelationshipCode = "S",
      subscriberEmploymentStartDate = "2018-01-01",
      employeeStatus = "E",
      permanentAddress = new Location(postalAddress),
      altId = "123",
      subscriberId = "1234",
      socialSecurityNumber = "123-45-6789",
      emailAddress = "john.doe@john.doe",
      cesCustomerName = "John Doe",
      cesCustomerNumber = "1234",
      subscriberName = personName,
      subscriberSSN = "123-45-6789",
      maritalStatus = "S",
      authoritativeRepresentative = List(authoritativeRepresentative),
      securityLevelCode = "TSS"
    )

    val result = new RallyEligibility(
      individualIdentifier = "cdb:680:1780429788:CS:45B2186",
      partitionNumber = "1",
      consumerId = "123",
      sourceCode = "TEST",
      legacySourceId = "ABC",
      xrefId = "mXbInfEmMjsdjkZ",
      xrefIdPartitionNumber = "mXbInfEmMjsdjkZ",
      personId = List(36L),
      demographics = demographics,
      healthCoverage = List(new HealthCoverage),
      healthServices = List(new HealthService),
      financialAccounts = List(new FinancialAccount),
      customerDefined = List(new CustomerDefined),
      coverageCustomDefined = List(new CoverageCustomDefined),
      memberPopulation = List(new MemberPopulation)
    )
    result
  }

  private def generateAvroKeyRecord(): RallyEligibilityWrapper[RallyEligibilityKey] = {
    new RallyEligibilityWrapper[RallyEligibilityKey](new RallyEligibilityKey("1"), Array.emptyByteArray)
  }

  private def generateConsumerRecords(avroRecord: RallyEligibility): ConsumerRecords[RallyEligibilityWrapper[RallyEligibilityKey], RallyEligibilityWrapper[RallyEligibility]] = {
    type keyType = RallyEligibilityWrapper[RallyEligibilityKey]
    type valueType = RallyEligibilityWrapper[RallyEligibility]

    val wrappedRecordValue = new RallyEligibilityWrapper[RallyEligibility](avroRecord, Array.emptyByteArray)
    val record = new ConsumerRecord[keyType, valueType]("test", 0, 0, generateAvroKeyRecord, wrappedRecordValue)
    val recordsMap = new java.util.HashMap[TopicPartition, java.util.List[ConsumerRecord[keyType, valueType]]]()
    val recordsList = new java.util.ArrayList[ConsumerRecord[keyType, valueType]]()

    val tp = new TopicPartition("test", 1)
    recordsList.add(record)
    recordsMap.put(tp, recordsList)

    new ConsumerRecords[keyType, valueType](recordsMap)
  }
}
