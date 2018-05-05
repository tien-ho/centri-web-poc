package com.rallyhealth.dataship.reader.controllers

import com.rallyhealth.spartan.v2.util.logging.DefaultLogger
import play.api.mvc.Action

class KafkaReaderControllerImpl
  extends KafkaReaderController with DefaultLogger {

  /**
    * HelloWorld type controller
    */
  override def getOffset = Action {
    implicit request =>
      {
        // $COVERAGE-ON$
        Ok("OK")
      }
  }
}
