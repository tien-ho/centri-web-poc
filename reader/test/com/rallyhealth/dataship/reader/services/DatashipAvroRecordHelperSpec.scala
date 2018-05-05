package com.rallyhealth.dataship.reader.services

import java.nio.charset.StandardCharsets

import com.rallyhealth.dataship.database.services.{DatashipDatabaseHmacService, DatashipDatabaseNeoEncryptionService}
import com.rallyhealth.dataship.reader.setup.DatashipFakeApplicationFactory
import org.scalatest.FunSpec
import org.scalatest.Matchers._

class DatashipAvroRecordHelperSpec extends FunSpec with DatashipFakeApplicationFactory {

  describe("buildInvividualDataEntity") {
    it("should build a valid individual data entity") {
      import RallyKafkaConsumerSpecHelper._
      implicit lazy val encryptionService = DatashipDatabaseNeoEncryptionService(datashipReaderModuleFromTestContext
        .configModule.datashipConfig.databaseEncryptionService
      )

      val dataEntity = DatashipAvroRecordHelper.buildIndividualDataEntity(
        validAvroRallyEligibility,
        "someString".getBytes(StandardCharsets.UTF_8),
        "someSchemaName",
        "someSchemaVersion"
      )

      dataEntity.isDefined shouldBe true
      dataEntity.head.individualId shouldBe validAvroRallyEligibility.individualIdentifier
    }

    it("should return None when source last updated timestamp does not exist") {
      import RallyKafkaConsumerSpecHelper._
      implicit lazy val encryptionService = DatashipDatabaseNeoEncryptionService(
        datashipReaderModuleFromTestContext
        .configModule
        .datashipConfig
        .databaseEncryptionService)

      val avroRecord = validAvroRallyEligibility
      avroRecord.sourceLastUpdatedTimestamp = None

      val dataEntity = DatashipAvroRecordHelper.buildIndividualDataEntity(
        avroRecord,
        "someString".getBytes(StandardCharsets.UTF_8),
        "someSchemaName",
        "someSchemaVersion"
      )

      dataEntity shouldBe None
    }

    it("should return None when source last updated timestamp cannot be parsed") {
      import RallyKafkaConsumerSpecHelper._
      implicit lazy val encryptionService = DatashipDatabaseNeoEncryptionService(
        datashipReaderModuleFromTestContext
        .configModule
        .datashipConfig
        .databaseEncryptionService)

      val avroRecord = validAvroRallyEligibility
      avroRecord.sourceLastUpdatedTimestamp = Some("somegibberish")

      val dataEntity = DatashipAvroRecordHelper.buildIndividualDataEntity(
        avroRecord,
        "someString".getBytes(StandardCharsets.UTF_8),
        "someSchemaName",
        "someSchemaVersion"
      )

      dataEntity shouldBe None
    }
  }

  describe("buildIndividualLookupEntity") {
    it("should None when personId list is empty") {
      import RallyKafkaConsumerSpecHelper._
      implicit lazy val hmacService = DatashipDatabaseHmacService(
        datashipReaderModuleFromTestContext
        .configModule
        .datashipConfig
        .databaseHmacService)

      val avroRecord = validAvroRallyEligibility
      avroRecord.personId = List.empty[Long]

      val lookupEntity = DatashipAvroRecordHelper.buildIndividualLookupEntity(avroRecord)

      lookupEntity shouldBe None
    }
  }
}
