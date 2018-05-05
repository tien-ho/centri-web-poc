package com.rallyhealth.dataship.database.models

/**
  * Trait that defines response returned from Dataship DAOs.
  */
sealed trait DatabaseResponse
case class DatabaseSuccessResponse() extends DatabaseResponse
case class DatabaseErrorResponse(message: String) extends DatabaseResponse
