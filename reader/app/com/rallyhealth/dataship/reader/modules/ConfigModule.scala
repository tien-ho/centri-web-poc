package com.rallyhealth.dataship.reader.modules

import com.rallyhealth.dataship.reader.configs.DatashipSecureLoggerConfig
import com.rallyhealth.dataship.reader.configs.DatashipReaderConfig
import com.rallyhealth.illuminati.v9.SecretConfig
import com.rallyhealth.optum.client.v8.common.{OptumClientConfig, OptumClientRallyConfig}
import com.rallyhealth.spartan.v2.config.RallyConfig
import com.softwaremill.macwire._

/**
  * Module for configs which the
  * services or clients depend on
  */
@Module
trait ConfigModule {
  def optumClientConfig: OptumClientConfig
  def datashipConfig: DatashipReaderConfig
  def secureLoggerConfig: DatashipSecureLoggerConfig
}

case class ConfigModuleImpl(rallyConfig: RallyConfig, secretConfig: SecretConfig) extends ConfigModule {
  override lazy val datashipConfig = wire[DatashipReaderConfig]
  override lazy val optumClientConfig = wire[OptumClientRallyConfig]
  override lazy val secureLoggerConfig = wire[DatashipSecureLoggerConfig]
}
