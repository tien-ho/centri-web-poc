package com.rallyhealth.dataship.database.dao

import com.rallyhealth.core.database.postgres.v2.RelationalDatabaseWrapper
import com.rallyhealth.dataship.database.helpers.DatashipDaoSpecHelper
import com.rallyhealth.dataship.database.models.{DatabaseErrorResponse, DatabaseSuccessResponse, IndividualDataEntity, IndividualLookupEntity}
import org.scalatest.BeforeAndAfterEach
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

class DataLocalizationDaoSpec extends DatashipDaoSpecHelper with BeforeAndAfterEach {
  import com.rallyhealth.dataship.database.helpers.DatashipDBTestFixtures._

  class Fixture {
    val databaseWrapper = new RelationalDatabaseWrapper(database.underlying, database.databaseConfig)
    val dataLocalizationDao = new DataLocalizationDaoImpl(databaseWrapper, individualLookupTable, individualDataTable)
  }

  // Create tables and insert default entities before every test.
  override def beforeEach(): Unit = {
    database.run(DBIO.seq(
      individualDataTable.schema.create,
      individualLookupTable.schema.create
    )).futureValue
  }

  // Clean up the tables after every test so that every test starts fresh.
  override def afterEach(): Unit = {
    database.run(DBIO.seq(
      individualLookupTable.schema.drop,
      individualDataTable.schema.drop
    )).futureValue
  }

  describe("insertBatch") {
    it("should insert valid entities") {
      val f = new Fixture
      import f._

      val result = dataLocalizationDao.insertBatch(
        Seq(individualLookupEntity),
        Seq(individualDataEntity)).futureValue

      result shouldBe a[DatabaseSuccessResponse]

      val numRowDataTable = database.run(individualDataTable.length.result).futureValue
      val numRowLookupTable = database.run(individualLookupTable.length.result).futureValue
      numRowDataTable shouldBe 1
      numRowLookupTable shouldBe 1

      val dataEntity = queryDataById(individualDataEntity.individualId).futureValue
      dataEntity.isDefined shouldBe true
      dataEntity.head.individualId shouldBe individualDataEntity.individualId
      dataEntity.head.consumerId shouldBe individualDataEntity.consumerId
      dataEntity.head.schemaName shouldBe individualDataEntity.schemaName
      dataEntity.head.schemaVersion shouldBe individualDataEntity.schemaVersion
      dataEntity.head.updatedAt shouldBe individualDataEntity.updatedAt
      dataEntity.head.createdAt shouldBe individualDataEntity.createdAt
      dataEntity.head.encryptedFullEligibilityRecord shouldBe individualDataEntity.encryptedFullEligibilityRecord

      val lookupEntity = queryLookupById(individualLookupEntity.individualId).futureValue
      lookupEntity.seq.size shouldBe 1
      lookupEntity.head.individualId shouldBe individualLookupEntity.individualId
      lookupEntity.head.consumerId shouldBe individualLookupEntity.consumerId
      lookupEntity.head.personId shouldBe individualLookupEntity.personId
      lookupEntity.head.xrefId shouldBe individualLookupEntity.xrefId
      lookupEntity.head.firstNameHash shouldBe individualLookupEntity.firstNameHash
      lookupEntity.head.lastNameHash shouldBe individualLookupEntity.lastNameHash
      lookupEntity.head.policyNumberHash shouldBe individualLookupEntity.policyNumberHash
      lookupEntity.head.searchIdHash shouldBe individualLookupEntity.searchIdHash
      lookupEntity.head.searchIdTypeHash shouldBe individualLookupEntity.searchIdTypeHash
      lookupEntity.head.dateOfBirthHash shouldBe individualLookupEntity.dateOfBirthHash
      lookupEntity.head.zipCodeHash shouldBe individualLookupEntity.zipCodeHash
    }

    it("should fail when inserting only lookup entity") {
      val f = new Fixture
      import f._

      val result = dataLocalizationDao.insertBatch(
        Seq(individualLookupEntity),
        Seq()).futureValue

      result shouldBe a[DatabaseErrorResponse]

      val numRowDataTable = database.run(individualDataTable.length.result).futureValue
      val numRowLookupTable = database.run(individualLookupTable.length.result).futureValue

      numRowDataTable shouldBe 0
      numRowLookupTable shouldBe 0
    }

    it("should succeed when inserting only data entity") {
      val f = new Fixture
      import f._

      val result = dataLocalizationDao.insertBatch(
        Seq(),
        Seq(individualDataEntity)).futureValue

      result shouldBe a[DatabaseSuccessResponse]

      val numRowDataTable = database.run(individualDataTable.length.result).futureValue
      val numRowLookupTable = database.run(individualLookupTable.length.result).futureValue

      numRowDataTable shouldBe 1
      numRowLookupTable shouldBe 0
    }
  }

  private def queryDataById(id: String): Future[Option[IndividualDataEntity]] = {
    val dbAction = individualDataTable.filter(_.individualId === id).result

    database.run(dbAction).map {
      data => data.headOption
    }
  }

  private def queryLookupById(id: String): Future[Seq[IndividualLookupEntity]] = {
    val dbAction = individualLookupTable.filter(_.individualId === id).result
    database.run(dbAction)
  }
}
