package com.rallyhealth.dataship.database.models

import slick.jdbc.PostgresProfile.api._

/**
  * This class defines individual_lookup table and maps fields to corresponding
  * Slick's Lifted Embedding types.
  *
  * http://slick.lightbend.com/doc/2.1.0/schemas.html
  */
class IndividualLookupTable(tag: Tag) extends Table[IndividualLookupEntity](tag, "individual_lookup") {

  // $COVERAGE-OFF$
  // Only slick manages this automatically increasing column.
  def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)
  // $COVERAGE-ON$

  def individualId: Rep[String] = column[String]("individual_id")
  def consumerId: Rep[String] = column[String]("consumer_id")
  def personId: Rep[Long] = column[Long]("person_id")
  def xrefId: Rep[Option[String]] = column[Option[String]]("xref_id")
  def firstNameHash: Rep[Hashed] = column[Hashed]("first_name_hash")
  def lastNameHash: Rep[Hashed] = column[Hashed]("last_name_hash")
  def policyNumberHash: Rep[Hashed] = column[Hashed]("policy_number_hash")
  def searchIdHash: Rep[Hashed] = column[Hashed]("search_id_hash")
  def searchIdTypeHash: Rep[Hashed] = column[Hashed]("search_id_type_hash")
  def dateOfBirthHash: Rep[Hashed] = column[Hashed]("date_of_birth_hash")
  def zipCodeHash: Rep[Hashed] = column[Hashed]("zipcode_hash")

  def individualIdFK = foreignKey("individual_id", individualId, TableQuery[IndividualDataTable])(_.individualId)

  private val fields = (
    individualId,
    consumerId,
    personId,
    xrefId,
    firstNameHash,
    lastNameHash,
    policyNumberHash,
    searchIdHash,
    searchIdTypeHash,
    dateOfBirthHash,
    zipCodeHash
  )

  override def * = fields <> ((IndividualLookupEntity.apply _).tupled, IndividualLookupEntity.unapply)
}
