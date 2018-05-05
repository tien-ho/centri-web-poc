package com.rallyhealth.dataship.database

import com.rallyhealth.core.database.postgres.v2.interfaces.{DatabaseConfig, DatabaseConfigDefaults}
import com.rallyhealth.dataship.database.DatashipDatabaseConfig.Defaults
import com.rallyhealth.illuminati.v9.SecretConfig
import com.rallyhealth.spartan.v2.config.RallyConfig

import scala.concurrent.duration._

/**
  * Dataship implementation of [[DatabaseConfig]]. Aside from the necessary configuration (e.g. the database
  * URL, name, and credentials), Dataship uses default settings defined by [[DatabaseConfig]].
  * We will define Dataship specific settings only when necessary.
  */
// $COVERAGE-OFF$
class DatashipDatabaseConfig(rallyConfig: RallyConfig, secretConfig: SecretConfig) extends DatabaseConfig {

  private val postgresHost = rallyConfig.get("dataship.postgres.db.url", Defaults.postgresHost)
  private val postgresPort = rallyConfig.get("dataship.postgres.db.port", Defaults.postgresPort)

  override val databaseName: String = rallyConfig.get("dataship.postgres.db.name", Defaults.postgresDbName)

  override val jdbcUrl: String = s"jdbc:postgresql://$postgresHost:$postgresPort/$databaseName"

  override val username: String = rallyConfig.get("dataship.postgres.db.username", Defaults.postgresUsername)

  override val password: String = secretConfig.get("dataship.postgres.db.password", Defaults.postgresPassoword).value

  override val queueSize: Int = rallyConfig.get("dataship.postgres.db.queuesize", DatabaseConfigDefaults.queueSize.toString).toInt

  override val maxPoolSize: Int = rallyConfig.get("dataship.postgres.db.poolsize", DatabaseConfigDefaults.maxPoolSize.toString).toInt

  override val connTimeoutMs: Int = rallyConfig.get("dataship.postgres.db.connection.timeoutMs", DatabaseConfigDefaults.connTimeout.toMillis.toString).toInt

  override val slowOpThresholdMs: Int = rallyConfig.get("dataship.postgres.db.slowop.thresholdMs", DatabaseConfigDefaults.slowOpThreshold.toMillis.toString).toInt
}

/**
  * This object holds default config values for Dataship database.
  */
object DatashipDatabaseConfig {
  object Defaults {
    val postgresDbName = "testdb"
    val postgresPort = "5432"
    val postgresHost = "localhost"
    val postgresUsername = "appuser"
    val postgresPassoword = "apppass"
  }
}
// $COVERAGE-ON$
