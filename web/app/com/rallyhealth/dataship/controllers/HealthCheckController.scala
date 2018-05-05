package com.rallyhealth.dataship.controllers

import com.rallyhealth.spartan.v2.util.logging.DefaultLogger
import play.api.mvc.{Controller, EssentialAction}

trait HealthCheckController  extends Controller with DefaultLogger {
    def healthStatus: EssentialAction
    def getHealthCheckStatus: EssentialAction
}
