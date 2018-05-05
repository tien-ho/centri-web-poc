package com.rallyhealth.dataship.modules

import com.rallyhealth.dataship.controllers._
import com.rallyhealth.enigma.v4.NeoEncryptionService
import com.rallyhealth.ops.StandardOpsController
import com.softwaremill.macwire._

import scala.concurrent.ExecutionContext

/**
  * Module that setups up all the Controller instances.
  */
@Module
trait ControllerModule {
  def greetController: GreetController
  def opsMonitorController: StandardOpsController
  def eligibilityController: EligibilityController
  def healthCheckController: HealthCheckController
}

/**
  * All Controller Dependencies are defined here.
  */
case class ControllerModuleImpl(serviceModule: ServiceModule, configModule: ConfigModule)(implicit ec: ExecutionContext) extends ControllerModule {
  implicit val apiNeoEncryptionService: NeoEncryptionService = configModule.datashipConfig.datashipApiEncryptionService
  override lazy val greetController: GreetController = wire[GreetControllerImpl]
  override lazy val opsMonitorController: StandardOpsController = wire[StandardOpsController]
  override lazy val eligibilityController: EligibilityController = wire[EligibilityControllerImpl]
    override lazy val healthCheckController: HealthCheckController = wire[HealthCheckControllerImpl]
}
