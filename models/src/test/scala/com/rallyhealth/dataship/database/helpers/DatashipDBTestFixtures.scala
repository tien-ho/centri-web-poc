package com.rallyhealth.dataship.database.helpers

import java.nio.charset.StandardCharsets

import com.rallyhealth.dataship.database.models.{Hashed, IndividualDataEntity, IndividualLookupEntity}
import org.joda.time.{DateTime, DateTimeZone}

/**
  * Default entity for each table to be used across specs.
  */
object DatashipDBTestFixtures {
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
    xrefId = Some("093726"),
    firstNameHash = Hashed("Harry"),
    lastNameHash = Hashed("Potter"),
    policyNumberHash = Hashed("somePolicyNumber"),
    searchIdHash = Hashed("someSearchId"),
    searchIdTypeHash = Hashed("someSearchIdType"),
    dateOfBirthHash = Hashed("04/02/1994"),
    zipCodeHash = Hashed("94043")
  )
}
