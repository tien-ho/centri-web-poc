package com.rallyhealth.dataship.database.dao

import com.rallyhealth.core.database.postgres.v2.interfaces.RelationalDatabase
import com.rallyhealth.dataship.database.models._
import com.rallyhealth.spartan.v2.util.logging.DefaultLogger
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

/**
  * Collection of database operations for individual lookup table.
  */
// TODO: DS-16: Return error detail in all operations
trait IndividualLookupDAO {

  /**
    * Finds all [[IndividualLookupEntity]] that matches given individualId.
    *
    * @param individualId The string value of individualId.
    * @return [[Seq]] of [[IndividualLookupEntity]] with the specified individualId.
    */
  def findByIndividualId(individualId: String): Future[Seq[IndividualLookupEntity]]

  /**
    * Inserts the given [[IndividualLookupEntity]] to the database with an assumption that a corresponding
    * individual data already exists with the same individualId.
    *
    * @param individualLookupRecord The [[IndividualLookupEntity]] that will be inserted.
    */
  def insert(individualLookupRecord: IndividualLookupEntity): Future[DatabaseResponse]
}

/**
  * The implementation of [[IndividualLookupDAO]] to access [[IndividualLookupEntity]].
  *
  * @param database [[RelationalDatabase]] to run database operations with stats.
  * @param individualLookupTable Slick's lifted table representation to execute database operations on the table of [[IndividualLookupEntity]].
  */
class IndividualLookupDaoImpl(
  database: RelationalDatabase,
  individualLookupTable: TableQuery[IndividualLookupTable]
)(implicit ec: ExecutionContext)
  extends IndividualLookupDAO
  with DefaultLogger {

  override def findByIndividualId(individualId: String): Future[Seq[IndividualLookupEntity]] = {
    val findAction = individualLookupTable.filter(_.individualId === individualId).result.asTry

    database.run(findAction).map {
      case Failure(error) =>
        logger.error(s"Failed to lookup records for individualId=${individualId}", error)
        Seq.empty
      case Success(records) if records.isEmpty =>
        logger.info(s"FindByIndividualId retrieved zero record!")
        Seq.empty
      case Success(records) if records.nonEmpty =>
        records.foreach(record =>
          logger.info(s"FindByIndividualId count=${records.size} individualId=${record.individualId}"))
        records
    }
  }

  override def insert(individualLookupRecord: IndividualLookupEntity): Future[DatabaseResponse] = {
    val insertAction = individualLookupTable += individualLookupRecord

    database.run(insertAction.asTry).map {
      case Success(_) =>
        logger.info(s"insert successful for individualId=${individualLookupRecord.individualId}.")
        DatabaseSuccessResponse()
      case Failure(error) =>
        logger.error(s"insert failed for individualId=${individualLookupRecord.individualId}.", error)
        DatabaseErrorResponse(s"Failed to insert individual lookup data. Error message=${error.getMessage} cause=${error.getCause}")
    }
  }
}
