package com.rallyhealth.dataship.modules

import com.rallyhealth.dataship.services.{MemPEService, MemPEServiceImpl}

import scala.concurrent.ExecutionContext
import com.softwaremill.macwire._

/**
  * Module for services
  */
@Module
trait ServiceModule {
  def memPEService: MemPEService
}

/**
  * Defines the Service Module and its dependencies
  */
case class ServiceModuleImpl(memPEClientModule: MemPEClientModule)(implicit ec: ExecutionContext) extends ServiceModule {

  override lazy val memPEService: MemPEService = wire[MemPEServiceImpl]

}
