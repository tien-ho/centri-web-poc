package com.rallyhealth.dataship.controllers

import com.rallyhealth.spartan.v2.util.logging.DefaultLogger
import play.api.mvc.{Controller, EssentialAction}

trait GreetController extends Controller with DefaultLogger {
  def greet: EssentialAction
}
