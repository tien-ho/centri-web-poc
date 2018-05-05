package com.rallyhealth.dataship.reader.controllers

import com.rallyhealth.spartan.v2.util.logging.DefaultLogger
import play.api.mvc.{Controller, EssentialAction}

trait KafkaReaderController extends Controller with DefaultLogger {
  def getOffset: EssentialAction
}
