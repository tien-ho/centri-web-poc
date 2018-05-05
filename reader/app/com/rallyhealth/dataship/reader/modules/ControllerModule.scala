package com.rallyhealth.dataship.reader.modules

import com.rallyhealth.dataship.reader.controllers.{KafkaReaderController, KafkaReaderControllerImpl}
import com.rallyhealth.dataship.reader.services.RallyKafkaConsumersService
import com.rallyhealth.ops.StandardOpsController
import com.softwaremill.macwire._

import scala.concurrent.ExecutionContext

/**
  * Module that setups up all the Controller instances.
  */
@Module
trait ControllerModule {
  def kafkaReaderController: KafkaReaderController
  def opsMonitorController: StandardOpsController
}

/**
  * All Controller Dependencies are defined here.
  */
case class ControllerModuleImpl(implicit ec: ExecutionContext) extends ControllerModule {
  override lazy val kafkaReaderController = wire[KafkaReaderControllerImpl]
  override lazy val opsMonitorController = wire[StandardOpsController]
}
