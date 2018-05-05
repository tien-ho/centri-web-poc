package com.rallyhealth.dataship.reader.services

import com.optum.exts.eligibility.rally.model.RallyEligibility
import com.rallyhealth.dataship.database.models._
import com.rallyhealth.dataship.database.services.{DatashipDatabaseHmacService, DatashipDatabaseNeoEncryptionService}
import com.rallyhealth.datetime.DateTimeHelpers
import com.rallyhealth.enigma.v4.{Encrypted, NeoEncryptionService}
import com.rallyhealth.spartan.v2.util.logging.DefaultLogger
import org.joda.time.{DateTime, DateTimeZone}

import scala.util.control.NonFatal
import scala.util.{Failure, Success}

object DatashipAvroRecordHelper extends DefaultLogger {

  /**
    * Validates if the specified JSON payload has necessary properties to build [[IndividualDataEntity]].
    * If the JSON payload is valid, the method returns a [[IndividualDataEntity]] with encrypted eligibility
    * record.
    *
    * Note: Parsing JSON payload to validate against [[IndividualDataEntity]] model is temporary as the
    * Avro message will be serialized into a model that has pre-generated from an Avro schema.
    */
  def buildIndividualDataEntity(
    eligibilityRecord: RallyEligibility,
    avroBytes: Array[Byte],
    schemaName: String,
    schemaVersion: String
  )(implicit datashipNeoEncryptionService: DatashipDatabaseNeoEncryptionService): Option[IndividualDataEntity] = {
    implicit val encryptionService: NeoEncryptionService = datashipNeoEncryptionService.neoEncryptionService

    try {
      val sourceLastUpdatedTime = eligibilityRecord.sourceLastUpdatedTimestamp match {
        case Some(timestamp) => DateTimeHelpers.tryToParse(timestamp) match {
          case Success(datetime) => datetime
          case Failure(_) => throw new RuntimeException("Failed to extract source updated time from the eligibility record.")
        }
        case None => throw new RuntimeException("Did not find source updated time from the eligibility record.")
      }

      val encryptedRecord = Encrypted.fromPlainBytes(avroBytes).cipherText

      Some(new IndividualDataEntity(
        eligibilityRecord.individualIdentifier,
        eligibilityRecord.consumerId,
        schemaVersion,
        schemaName,
        new DateTime(DateTimeZone.UTC),
        new DateTime(DateTimeZone.UTC),
        sourceLastUpdatedTime,
        encryptedRecord
      ))
    } catch {
      // Any deserialization or parsing error will result in returning None
      case NonFatal(_) => None
    }
  }

  def buildIndividualLookupEntity(eligibilityRecord: RallyEligibility)(implicit datashipHmacService: DatashipDatabaseHmacService): Option[IndividualLookupEntity] = {

    try {
      val demographicInfo = eligibilityRecord.demographics
      val individualId = eligibilityRecord.individualIdentifier
      val postalAddress = eligibilityRecord.demographics.permanentAddress.postalAddress.zip
      val xrefId = Option(eligibilityRecord.xrefId)

      val consumerId = eligibilityRecord.consumerId
      val personId = eligibilityRecord.personId(0)
      val firstNameHash = Hashed.fromPlainString(demographicInfo.personName.firstName)
      val lastNameHash = Hashed.fromPlainString(demographicInfo.personName.lastName)
      val policyNumberHash = Hashed.fromPlainString(eligibilityRecord.healthCoverage(0).policyNumber)
      val birthDateHash = Hashed.fromPlainString(demographicInfo.dateOfBirth)
      val searchIdHash = Hashed.fromPlainString(demographicInfo.socialSecurityNumber)
      val searchIdTypeHash = Hashed.fromPlainString("SocialSecurityNumber")
      val zipCodeHash = Hashed.fromPlainString(postalAddress)

      Some(IndividualLookupEntity(
        individualId,
        consumerId,
        personId,
        xrefId,
        firstNameHash,
        lastNameHash,
        policyNumberHash,
        searchIdHash,
        searchIdTypeHash,
        birthDateHash,
        zipCodeHash
      ))
    } catch {
      // Any deserialization or parsing error will result in returning None
      case NonFatal(_) => None
    }
  }
}
