package com.rallyhealth.dataship.database.models

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

import scala.util.Try

/**
  * Represents the DB entity of individual_lookup table.
  *
  * This entry contains queryable fields to search for individual eligibility records in
  * the individual_data table.
  *
  * @param individualId A unique identifier of individual data record, made up of
  *                     {consumerId + partitionNumber + sourceCode + legacySourceId}
  * @param consumerId Primary key for a customer in CDB.
  * @param personId A unique identifier of a customer. Generated by CIS.
  * @param xrefId A unique identifier at the same level as the individualId and used
  *               when merging and splitting individual records.
  */
case class IndividualLookupEntity(
  individualId: String,
  consumerId: String,
  personId: Long,
  xrefId: Option[String],
  firstNameHash: Hashed,
  lastNameHash: Hashed,
  policyNumberHash: Hashed,
  searchIdHash: Hashed,
  searchIdTypeHash: Hashed,
  dateOfBirthHash: Hashed,
  zipCodeHash: Hashed
)
