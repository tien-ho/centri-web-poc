package com.rallyhealth.dataship.controllers

import play.api.mvc.{Controller, EssentialAction}

/**
  * The entry point to load eligibility data from Dataship.  For Phase 1 this endpoint is intended to behave the same
  * way that Optum's MemPE endpoint does today so that it can be a direct replacement.
  */
trait EligibilityController extends Controller {

  /**
    * For now this is a direct passthrough to Optum's MemPE with the addition decryption/encryption of the
    * request/response.  Clients should be able to switch from using MemPE to using this method directly with the
    * requirement that the client encrypt the payload.
    * @return The encrypted response from Optum's MemPE endpoint
    */
  def getEligibility: EssentialAction
}
