package com.rallyhealth.dataship.database.dao

import java.nio.charset.StandardCharsets

import com.rallyhealth.core.database.postgres.v2.RelationalDatabaseWrapper
import com.rallyhealth.dataship.database.helpers.DatashipDaoSpecHelper
import com.rallyhealth.dataship.database.models.IndividualDataEntity
import org.joda.time.{DateTime, DateTimeZone}
import org.scalatest.BeforeAndAfterEach
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

class IndividualDataDAOSpec extends DatashipDaoSpecHelper with BeforeAndAfterEach {

  import com.rallyhealth.dataship.database.helpers.DatashipDBTestFixtures._

  class Fixture {
    val databaseWrapper = new RelationalDatabaseWrapper(database.underlying, database.databaseConfig)
    val individualDataDao = new IndividualDataDAOImpl(databaseWrapper, individualDataTable)
  }

  // Create tables and insert default entities before every test.
  override def beforeEach(): Unit = {
    database.run(DBIO.seq(
      individualDataTable.schema.create,
      individualDataTable += individualDataEntity
    )).futureValue
  }

  // Clean up the tables after every test so that every test starts fresh.
  override def afterEach(): Unit = {
    database.run(individualDataTable.schema.drop).futureValue
  }

  describe("findByIndividualId") {
    it("should get individual data by existing individual id") {
      val f = new Fixture
      val result = f.individualDataDao.findByIndividualId("someIndividualId").futureValue
      result.isDefined shouldBe true
      assert(result.head.individualId === "someIndividualId")
      assert(result.head.schemaVersion === "someSchemaVersion")
      assert(result.head.schemaName === "someSchemaName")
    }

    it("should not get any individual data by non-existing individual id") {
      val f = new Fixture
      val result = f.individualDataDao.findByIndividualId("badIndividualId").futureValue
      result.isEmpty shouldBe true
    }
  }

  describe("insertOrUpdate") {
    it("should insert when record does not exist") {
      val f = new Fixture
      f.individualDataDao.insertOrUpdate(
        IndividualDataEntity(
          "newIndividualId",
          "98765",
          "newSchemaVersion",
          "newSchemaName",
          new DateTime(DateTimeZone.UTC),
          new DateTime(DateTimeZone.UTC),
          new DateTime(DateTimeZone.UTC),
          "newEligibilityRecord".getBytes(StandardCharsets.UTF_8)
        )).futureValue

      val result = queryById("newIndividualId").futureValue
      result.isDefined shouldBe true
      assert(result.head.individualId === "newIndividualId")
      assert(result.head.consumerId === "98765")
      assert(result.head.schemaVersion === "newSchemaVersion")
      assert(result.head.schemaName === "newSchemaName")
    }

    it("should update when record with same individualId already exists") {
      val f = new Fixture
      val entity = individualDataEntity.copy(schemaName = "newSchemaName")
      f.individualDataDao.insertOrUpdate(entity).futureValue

      val result = queryById("someIndividualId").futureValue
      result.isDefined shouldBe true
      assert(result.head.individualId === "someIndividualId")
      assert(result.head.consumerId === "12345")
      assert(result.head.schemaVersion === "someSchemaVersion")
      assert(result.head.schemaName === "newSchemaName")
    }

    it("should insert new when record has different individualId") {
      val f = new Fixture
      val entity = individualDataEntity.copy(individualId = "newIndividualId")
      f.individualDataDao.insertOrUpdate(entity).futureValue

      // Validate newly inserted record
      val result = queryById("newIndividualId").futureValue
      result.isDefined shouldBe true
      assert(result.head.individualId === "newIndividualId")
      assert(result.head.schemaVersion === "someSchemaVersion")
      assert(result.head.schemaName === "someSchemaName")

      // Validate that old record is still retrievable
      val result2 = queryById("someIndividualId").futureValue
      result2.isDefined shouldBe true
      assert(result2.head.individualId === "someIndividualId")
      assert(result2.head.schemaVersion === "someSchemaVersion")
      assert(result2.head.schemaName === "someSchemaName")
    }

    it("should not update when schema version is null") {
      val f = new Fixture
      val entity = individualDataEntity.copy(schemaVersion = null)
      f.individualDataDao.insertOrUpdate(entity).futureValue

      val result = queryById(individualDataEntity.individualId).futureValue
      result.isDefined shouldBe true
      assert(result.head.individualId === individualDataEntity.individualId)
      assert(result.head.consumerId === individualDataEntity.consumerId)
      assert(result.head.schemaVersion === individualDataEntity.schemaVersion)
      assert(result.head.schemaName === individualDataEntity.schemaName)
      assert(result.head.updatedAt === individualDataEntity.updatedAt)
      assert(result.head.createdAt === individualDataEntity.createdAt)
      assert(result.head.encryptedFullEligibilityRecord === individualDataEntity.encryptedFullEligibilityRecord)
    }

    it("should not update when schema name is null") {
      val f = new Fixture
      val entity = individualDataEntity.copy(schemaName = null)
      f.individualDataDao.insertOrUpdate(entity).futureValue

      val result = queryById(individualDataEntity.individualId).futureValue
      result.isDefined shouldBe true
      assert(result.head.individualId === individualDataEntity.individualId)
      assert(result.head.consumerId === individualDataEntity.consumerId)
      assert(result.head.schemaVersion === individualDataEntity.schemaVersion)
      assert(result.head.schemaName === individualDataEntity.schemaName)
      assert(result.head.updatedAt === individualDataEntity.updatedAt)
      assert(result.head.createdAt === individualDataEntity.createdAt)
      assert(result.head.encryptedFullEligibilityRecord === individualDataEntity.encryptedFullEligibilityRecord)
    }

    it("should not update when eligibility record is null") {
      val f = new Fixture
      val entity = individualDataEntity.copy(encryptedFullEligibilityRecord = null)
      f.individualDataDao.insertOrUpdate(entity).futureValue

      val result = queryById(individualDataEntity.individualId).futureValue
      result.isDefined shouldBe true
      assert(result.head.individualId === individualDataEntity.individualId)
      assert(result.head.consumerId === individualDataEntity.consumerId)
      assert(result.head.schemaVersion === individualDataEntity.schemaVersion)
      assert(result.head.schemaName === individualDataEntity.schemaName)
      assert(result.head.updatedAt === individualDataEntity.updatedAt)
      assert(result.head.createdAt === individualDataEntity.createdAt)
      assert(result.head.encryptedFullEligibilityRecord === individualDataEntity.encryptedFullEligibilityRecord)
    }
  }

  private def queryById(id: String): Future[Option[IndividualDataEntity]] = {
    val dbAction = individualDataTable.filter(_.individualId === id).result

    database.run(dbAction).map {
      data => data.headOption
    }
  }
}
