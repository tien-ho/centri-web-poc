package com.rallyhealth.dataship.database.modules

import com.rallyhealth.core.database.postgres.v2.PostgresProfile
import com.rallyhealth.core.database.postgres.v2.evolutions.PlayDBImpl
import com.rallyhealth.core.database.postgres.v2.interfaces.DatabaseConfig
import com.rallyhealth.dataship.database.DatashipDatabaseConfig
import com.rallyhealth.dataship.database.dao._
import com.rallyhealth.illuminati.v9.SecretConfig
import com.rallyhealth.spartan.v2.config.RallyConfig
import com.softwaremill.macwire._
import play.api.db.DBApi

import scala.concurrent.ExecutionContext

/**
  * [[DatabaseModule]] is the main module for constructing and configuring all constructs needed
  * for Postgres operations. With configs provided by the running application, this module constructs
  * the connection pool for the database source, the database wrapper for running DB operations, and
  * the implementation of DBApi for schema evolutions.
  *
  * @param rallyConfig [[RallyConfig]] from the running application with Postgres database configs.
  * @param secretConfig [[SecretConfig]] from the running application with Postgres database password.
  */
// $COVERAGE-OFF$
@Module
class DatabaseModule(
  rallyConfig: RallyConfig,
  secretConfig: SecretConfig
)(implicit val executionContext: ExecutionContext) extends PostgresProfile with DatabaseTableModule {

  // Config
  override lazy val databaseConfig: DatabaseConfig = wire[DatashipDatabaseConfig]

  // Evolutions
  lazy val dbApi: DBApi = PlayDBImpl(
    databaseConfig,
    hikariConfig,
    hikariDataSource,
    jdbcDataSource
  )

  // DAOs
  lazy val dataLocalizationDao: DataLocalizationDao = wire[DataLocalizationDaoImpl]
  lazy val individualLookupDao: IndividualLookupDAO = wire[IndividualLookupDaoImpl]
  lazy val individualDataDao: IndividualDataDAO = wire[IndividualDataDAOImpl]
}
// $COVERAGE-ON$
