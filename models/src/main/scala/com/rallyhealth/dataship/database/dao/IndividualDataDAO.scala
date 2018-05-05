package com.rallyhealth.dataship.database.dao

import com.rallyhealth.core.database.postgres.v2.interfaces.RelationalDatabase
import com.rallyhealth.dataship.database.models._
import com.rallyhealth.spartan.v2.util.logging.DefaultLogger
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

/**
  * Collection of database operations for individual data table.
  * The individual data table consists  of rows of [[IndividualDataEntity]], which essentially holds
  * full eligibility record from Optum CDB.
  */
// TODO: DS-16: Return error detail in all operations
trait IndividualDataDAO {

  /**
    * Finds an individual data (i.e. [[IndividualDataEntity]]) by the given individualId.
    *
    * @param individualId The unique ID that references an individual data with full eligibility record.
    * @return The individual data with the given individualId.
    */
  def findByIndividualId(individualId: String): Future[Option[IndividualDataEntity]]

  /**
    * Inserts the given [[IndividualDataEntity]] to the individual data table. If the entity with the same individualId already exists,
    * the operation will update the existing entity.
    * @param data The [[IndividualDataEntity]] that will be either inserted or updated.
    */
  def insertOrUpdate(data: IndividualDataEntity): Future[DatabaseResponse]
}

class IndividualDataDAOImpl(
  database: RelationalDatabase,
  table: TableQuery[IndividualDataTable]
)(implicit executionContext: ExecutionContext) extends IndividualDataDAO with DefaultLogger {

  override def findByIndividualId(individualId: String): Future[Option[IndividualDataEntity]] = {
    val dbAction = table.filter(_.individualId === individualId).result

    database.run(dbAction).map {
      data =>
        if (data.isEmpty) logger.info(s"Could not find any data with id=$individualId")
        data.headOption
    }
  }

  override def insertOrUpdate(data: IndividualDataEntity): Future[DatabaseResponse] = {
    val upsertAction = table.insertOrUpdate(data)

    database.run(upsertAction.asTry).map {
      case Success(_) =>
        logger.info(s"insertOrUpdate successful for individualId=${data.individualId}")
        DatabaseSuccessResponse()
      case Failure(error) =>
        logger.error(s"insertOrUpdate failed for individualId=${data.individualId}.", error)
        DatabaseErrorResponse(s"Failed to upsert individual data. Error message=${error.getMessage} cause=${error.getCause}")
    }
  }
}
