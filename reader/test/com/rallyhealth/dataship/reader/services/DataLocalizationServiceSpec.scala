package com.rallyhealth.dataship.reader.services

import java.nio.charset.StandardCharsets

import com.rallyhealth.dataship.database.dao.DataLocalizationDao
import com.rallyhealth.dataship.database.models.{DatabaseSuccessResponse, Hashed, IndividualDataEntity, IndividualLookupEntity}
import org.joda.time.{DateTime, DateTimeZone}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FunSpec, Matchers}
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito._

import scala.concurrent.Future

class DataLocalizationServiceSpec extends FunSpec with MockitoSugar with ScalaFutures with Matchers {
  class Fixture {
    val dataLocalizationDao = mock[DataLocalizationDao]
    when(dataLocalizationDao.insertBatch(any(), any())).thenReturn(Future.successful(DatabaseSuccessResponse()))
    val dataLocalizationService = new DataLocalizationServiceImpl(dataLocalizationDao)
  }

  describe("insertBatch") {
    it("should insert") {
      val f = new Fixture
      import f._

      lazy val individualDataEntity = IndividualDataEntity(
        "someIndividualId",
        "12345",
        "someSchemaVersion",
        "someSchemaName",
        new DateTime(DateTimeZone.UTC),
        new DateTime(DateTimeZone.UTC),
        new DateTime(DateTimeZone.UTC),
        "someEligibilityRecord".getBytes(StandardCharsets.UTF_8)
      )

      lazy val individualLookupEntity = IndividualLookupEntity(
        individualId = "someIndividualId",
        consumerId = "12345",
        personId = 987654,
        xrefId = Some("0482526260"),
        firstNameHash = Hashed("Harry"),
        lastNameHash = Hashed("Potter"),
        policyNumberHash = Hashed("somePolicyNumber"),
        searchIdHash = Hashed("someSearchId"),
        searchIdTypeHash = Hashed("someSearchIdType"),
        dateOfBirthHash = Hashed("04/02/1994"),
        zipCodeHash = Hashed("94043")
      )

      val response = dataLocalizationService.insertBatch(
        Seq(individualLookupEntity),
        Seq(individualDataEntity)
      ).futureValue

      response shouldBe a[DatabaseSuccessResponse]
    }
  }
}
