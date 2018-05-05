package com.rallyhealth.dataship.database.dao

import com.rallyhealth.core.database.postgres.v2.RelationalDatabaseWrapper
import com.rallyhealth.dataship.database.helpers.DatashipDaoSpecHelper
import com.rallyhealth.dataship.database.models.{Hashed, IndividualLookupEntity}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.concurrent.ScalaFutures
import slick.jdbc.PostgresProfile.api._
import slick.jdbc.meta.MTable

import scala.concurrent.Future

class IndividualLookupDAOSpec
  extends DatashipDaoSpecHelper
  with BeforeAndAfterEach
  with ScalaFutures {

  import com.rallyhealth.dataship.database.helpers.DatashipDBTestFixtures._

  class Fixture {
    val databaseWrapper = new RelationalDatabaseWrapper(database.underlying, database.databaseConfig)
    val individualLookupDao = new IndividualLookupDaoImpl(databaseWrapper, individualLookupTable)
  }

  // Create tables and insert default entities before every test.
  override def beforeEach(): Unit = {
    database.run(DBIO.seq(
      individualDataTable.schema.create,
      individualLookupTable.schema.create,
      individualDataTable += individualDataEntity,
      individualLookupTable += individualLookupEntity
    )).futureValue
  }

  // Clean up all tables after every test so that every test starts fresh.
  override def afterEach(): Unit = {
    database.run(DBIO.seq(
      individualLookupTable.schema.drop,
      individualDataTable.schema.drop
    )).futureValue
  }

  describe("findByIndividualId") {
    it("should get individual lookup entity by existing individual id") {
      val f = new Fixture
      val result = f.individualLookupDao.findByIndividualId("someIndividualId").futureValue

      result.isEmpty shouldBe false
      assert(result.seq.size === 1)
      assert(result.head.individualId === "someIndividualId")
      assert(result.head.consumerId === "12345")
      assert(result.head.personId === 987654)
      assert(result.head.xrefId === Some("093726"))
      assert(result.head.firstNameHash === Hashed("Harry"))
      assert(result.head.lastNameHash === Hashed("Potter"))
      assert(result.head.policyNumberHash === Hashed("somePolicyNumber"))
      assert(result.head.searchIdHash === Hashed("someSearchId"))
      assert(result.head.searchIdTypeHash === Hashed("someSearchIdType"))
      assert(result.head.dateOfBirthHash === Hashed("04/02/1994"))
      assert(result.head.zipCodeHash === Hashed("94043"))
    }

    it("should not get any individual data by non-existing individual id") {
      val f = new Fixture
      val result = f.individualLookupDao.findByIndividualId("badIndividualId").futureValue
      result.isEmpty shouldBe true
    }

    it("should not get any individual data when table does not exist.") {
      val f = new Fixture
      try {
        database.run(DBIO.seq(
          individualLookupTable.schema.drop,
          individualDataTable.schema.drop
        )).futureValue

        whenReady(f.individualLookupDao.findByIndividualId(null)) {
          result => result.isEmpty shouldBe true
        }
      } finally {
        // Recreate the tables so that afterEach() can perform proper clean up.
        database.run(DBIO.seq(
          individualDataTable.schema.create,
          individualLookupTable.schema.create
        )).futureValue
      }
    }
  }

  describe("insert") {
    it("should insert when record does not exist") {
      val f = new Fixture
      // Need to add a data entity with new Id first due to foreign key constraint
      insertDataEntityById("newIndividualId").futureValue shouldBe 1

      val lookupEntity = individualLookupEntity.copy(individualId = "newIndividualId")
      f.individualLookupDao.insert(lookupEntity).futureValue

      // Validate the entity that was just inserted
      val result = queryById("newIndividualId").futureValue
      result.headOption.isDefined shouldBe true
      assert(result.head.individualId === "newIndividualId")

      // Validate the default entity
      val oldResult = queryById("someIndividualId").futureValue
      oldResult.headOption.isDefined shouldBe true
      assert(oldResult.head.individualId === "someIndividualId")
    }

    it("should insert new record even when record with same individualId already exists") {
      val f = new Fixture
      val entity = individualLookupEntity.copy()
      f.individualLookupDao.insert(entity).futureValue

      val result = queryById("someIndividualId").futureValue
      result.size shouldBe 2
      result.foreach(record => {
        assert(record.individualId === "someIndividualId")
        assert(record.firstNameHash === Hashed("Harry"))
        assert(record.lastNameHash === Hashed("Potter"))
      })
    }

    it("should fail to insert when table does not exist") {
      val f = new Fixture
      try {
        database.run(DBIO.seq(
          individualLookupTable.schema.drop,
          individualDataTable.schema.drop
        )).futureValue

        val result = f.individualLookupDao.insert(individualLookupEntity).futureValue

        val lookupTable = database.run(MTable.getTables(individualLookupTable.baseTableRow.tableName)).futureValue.toList
        assert(lookupTable.isEmpty)

      } finally {
        // Recreate the tables so that afterEach() can perform proper clean up.
        database.run(DBIO.seq(
          individualDataTable.schema.create,
          individualLookupTable.schema.create
        )).futureValue
      }
    }
  }

  private def queryById(id: String): Future[Seq[IndividualLookupEntity]] = {
    val dbAction = individualLookupTable.filter(_.individualId === id).result
    database.run(dbAction)
  }

  private def insertDataEntityById(id: String): Future[Int] = {
    val dataEntity = individualDataEntity.copy(individualId = id)
    val insertAction = individualDataTable.insertOrUpdate(dataEntity)
    database.run(insertAction)
  }

}
