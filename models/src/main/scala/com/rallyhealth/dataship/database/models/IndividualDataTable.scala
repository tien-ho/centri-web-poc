package com.rallyhealth.dataship.database.models

import com.rallyhealth.core.database.postgres.v2.extensions.MappedJodaTime._
import org.joda.time.DateTime
import slick.jdbc.PostgresProfile.api._

/**
  * This class defines individual_data table and maps fields to corresponding
  * Slick's Lifted Embedding types.
  *
  * http://slick.lightbend.com/doc/2.1.0/schemas.html
  */
class IndividualDataTable(tag: Tag)
  extends Table[IndividualDataEntity](tag, "individual_data") {

  def individualId: Rep[String] = column[String]("individual_id", O.PrimaryKey)
  def consumerId: Rep[String] = column[String]("consumer_id")
  def recordSchemaVersion: Rep[String] = column[String]("schema_version")
  def recordSchemaName: Rep[String] = column[String]("schema_name")
  def createdAt: Rep[DateTime] = column[DateTime]("created_at")
  def updatedAt: Rep[DateTime] = column[DateTime]("updated_at")
  def sourceUpdatedAt: Rep[DateTime] = column[DateTime]("source_updated_at")
  def encryptedFullEligibilityRecord: Rep[Array[Byte]] = column[Array[Byte]]("encrypted_full_eligibility_record")

  private val fields = (
    individualId,
    consumerId,
    recordSchemaVersion,
    recordSchemaName,
    createdAt,
    updatedAt,
    sourceUpdatedAt,
    encryptedFullEligibilityRecord
  )

  override def * = fields <> ((IndividualDataEntity.apply _).tupled, IndividualDataEntity.unapply)
}
