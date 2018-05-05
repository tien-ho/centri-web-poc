package com.rallyhealth.dataship.reader.services

import com.rallyhealth.dataship.database.dao.{DataLocalizationDao, IndividualDataDAO}
import com.rallyhealth.dataship.database.models._
import com.rallyhealth.spartan.v2.util.logging.DefaultLogger
import org.joda.time.{DateTime, DateTimeZone}

import scala.concurrent.Future

/**
  * Service that localizes Optum CDB data into Dataship DB.
  */
trait DataLocalizationService {

  /**
    * Inserts [[Seq]]s of [[IndividualLookupEntity]] and [[IndividualDataEntity]].
    *
    * In the first phase, this function simply calls the DAO, but the service will eventually need to expand to
    * handle better delta updates for [[IndividualLookupEntity]].
    */
  def insertBatch(lookupEntities: Seq[IndividualLookupEntity], dataEntities: Seq[IndividualDataEntity]): Future[DatabaseResponse]
}

class DataLocalizationServiceImpl(
  dataLocalizationDao: DataLocalizationDao
) extends DataLocalizationService with DefaultLogger {
  override def insertBatch(lookupEntities: Seq[IndividualLookupEntity], dataEntities: Seq[IndividualDataEntity]): Future[DatabaseResponse] = {
    dataLocalizationDao.insertBatch(lookupEntities, dataEntities)
  }
}
