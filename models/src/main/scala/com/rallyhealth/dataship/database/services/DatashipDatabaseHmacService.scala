package com.rallyhealth.dataship.database.services

import com.rallyhealth.enigma.v4.HMacService

/**
  * Wrapping [[HMacService]] to disambiguate Dataship's [[HMacService]] from other
  * [[HMacService]]. This way, [[HMacService]] can be passed around implicitly and safely in the
  * Dataship application.
  *
  * @param hmacService Underlying [[HMacService]] to hash messages that should not be human readable.
  */
case class DatashipDatabaseHmacService(hmacService: HMacService)
