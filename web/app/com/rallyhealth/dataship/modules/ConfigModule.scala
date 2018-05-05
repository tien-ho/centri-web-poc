package com.rallyhealth.dataship.modules

import com.rallyhealth.dataship.configs.DatashipSecureLoggerConfig
import com.rallyhealth.dataship.configs.DatashipConfig
import com.rallyhealth.illuminati.v9.SecretConfig
import com.rallyhealth.optum.client.v8.common.{OptumClientConfig, OptumClientRallyConfig}
import com.rallyhealth.optum.client.v8.member.config.MemberProductEligibilityRallyConfig
import com.rallyhealth.spartan.v2.config.RallyConfig
import com.softwaremill.macwire._

/**
  * Module for configs which the
  * services or clients depend on
  */
@Module
trait ConfigModule {
  def datashipConfig: DatashipConfig
  def secureLoggerConfig: DatashipSecureLoggerConfig
  def memPeClientConfig: MemberProductEligibilityRallyConfig
}

case class ConfigModuleImpl(rallyConfig: RallyConfig, secretConfig: SecretConfig) extends ConfigModule {
  override lazy val datashipConfig = wire[DatashipConfig]
  override lazy val secureLoggerConfig = wire[DatashipSecureLoggerConfig]
  override lazy val memPeClientConfig: MemberProductEligibilityRallyConfig = wire[MemberProductEligibilityRallyConfig]
}
