package com.rallyhealth.dataship.database.dao

import java.time.Instant

import com.rallyhealth.core.database.postgres.v2.interfaces.RelationalDatabase
import com.rallyhealth.dataship.database.models._
import com.rallyhealth.spartan.v2.util.logging.DefaultLogger
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

/**
  * DAO that works with both individual_data and individual_lookup tables.
  */
trait DataLocalizationDao {
  /**
    * Inserts the given sequences of [[IndividualLookupEntity]] and [[IndividualDataEntity]] in batch.
    *
    * @param lookupEntities [[Seq]] of [[IndividualLookupEntity]] to upsert.
    * @param dataEntities [[Seq]] of [[IndividualLookupEntity]] to insert.
    * @return [[DatabaseResponse]] to indicate result of the DB operation.
    */
  def insertBatch(lookupEntities: Seq[IndividualLookupEntity], dataEntities: Seq[IndividualDataEntity]): Future[DatabaseResponse]
}

class DataLocalizationDaoImpl(
  database: RelationalDatabase,
  lookupTable: TableQuery[IndividualLookupTable],
  dataTable: TableQuery[IndividualDataTable]
)(implicit executionContext: ExecutionContext) extends DataLocalizationDao with DefaultLogger {

  override def insertBatch(
    lookupEntities: Seq[IndividualLookupEntity],
    dataEntities: Seq[IndividualDataEntity]
  ): Future[DatabaseResponse] = {
    val startTime = Instant.now().toEpochMilli
    val upsertDataAction = dataEntities.map(entity => dataTable.insertOrUpdate(entity))
    val insertLookupAction = lookupTable ++= lookupEntities

    val dbio = for {
      _ <- DBIO.sequence(upsertDataAction)
      _ <- insertLookupAction
    } yield ()

    database.run(dbio.transactionally.asTry).map {
      case Success(_) =>
        val duration = Instant.now().toEpochMilli - startTime
        logger.info(s"insert successful for batch size=${dataEntities.size} duration=${duration}")
        DatabaseSuccessResponse()

      case Failure(error) =>
        logger.error(s"insert failed for batch size=${dataEntities.size}.", error)
        DatabaseErrorResponse(s"insert failed message=${error.getMessage} cause=${error.getCause}")
    }
  }
}
