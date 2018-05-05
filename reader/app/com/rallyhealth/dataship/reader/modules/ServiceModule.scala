package com.rallyhealth.dataship.reader.modules

import akka.actor.Scheduler
import com.rallyhealth.dataship.database.dao.{DataLocalizationDao, IndividualDataDAO, IndividualLookupDAO}
import com.rallyhealth.dataship.reader.configs.DatashipReaderConfig
import com.rallyhealth.dataship.reader.crypto.KeyDecryptor
import com.rallyhealth.dataship.reader.services._
import com.softwaremill.macwire._

import scala.concurrent.ExecutionContext

@Module
class ServiceModule(
  individualLookupDao: IndividualLookupDAO,
  individualDataDao: IndividualDataDAO,
  dataLocalizationDao: DataLocalizationDao,
  datashipReaderConfig: DatashipReaderConfig,
  maybeKeyDecryptor: Option[KeyDecryptor] = None,
  scheduler: Scheduler
)(implicit ec: ExecutionContext) {
  lazy val encryptedEnvelopeDecrypterService: EncryptedMessageDecrypterService = wire[EncryptedMessageDecrypterServiceImpl]
  lazy val dataLocalizationService: DataLocalizationService = wire[DataLocalizationServiceImpl]
  lazy val rallyKafkaConsumersService: RallyKafkaConsumersService = wire[RallyKafkaConsumersServiceImpl]
}
