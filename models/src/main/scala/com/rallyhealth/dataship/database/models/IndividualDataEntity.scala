package com.rallyhealth.dataship.database.models

import org.joda.time.{DateTime, DateTimeZone}
import play.api.libs.functional.syntax._
import play.api.libs.json._

/**
  * Represents the DB entity of individual_data table.
  *
  * This entity contains the actual eligibility record that has been materialized
  * from Optum CDB. CDB will assign individual ID to uniquely identify the eligibility record.
  *
  * @param individualId A unique identifier for individual record.
  * @param consumerId Primary key for customer in CDB.
  * @param schemaVersion Version of the eligibility record schema.
  * @param schemaName Name of the eligibility record schema.
  * @param createdAt [[DateTime]] when the entity has been created.
  * @param updatedAt Last [[DateTime]] when the entity has updated.
  * @param sourceUpdatedAt Last [[DateTime]] when the eligibility record has been updated in CDB.
  * @param encryptedFullEligibilityRecord Raw bytes of encrypted eligibility record from CDB.
  */
case class IndividualDataEntity(
  individualId: String,
  consumerId: String,
  schemaVersion: String,
  schemaName: String,
  createdAt: DateTime,
  updatedAt: DateTime,
  sourceUpdatedAt: DateTime,
  encryptedFullEligibilityRecord: Array[Byte]
)
