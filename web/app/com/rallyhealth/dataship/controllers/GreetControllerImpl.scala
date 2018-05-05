package com.rallyhealth.dataship.controllers

import com.rallyhealth.spartan.v2.util.logging.DefaultLogger
import play.api.mvc.Action

class GreetControllerImpl extends GreetController with DefaultLogger {

  override def greet = Action {
    implicit request =>
      Ok("greets to you")
  }
}
