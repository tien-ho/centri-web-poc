package com.rallyhealth.dataship.services

import com.rallyhealth.optum.client.v8.common.model.{BaseOptumResponseError, OptumResponseSuccess}
import com.rallyhealth.optum.client.v8.member.ws.MemPERequestBodyBuilder.MemberProductEligibilityRequest

import scala.concurrent.{ExecutionContext, Future}

trait CentriPEService {

    /**
      * Calls the Optum MemPE API
      *
      * @param request The MemberProductEligibilityRequest coming in from the client
      * @return Returns either an ErrorResponse from MemPE or a model containing a user's EligibilityRecords
      */
    def memberProductEligibility(request: MemberProductEligibilityRequest,
                                 correlationId: Option[String])(implicit ec: ExecutionContext): Future[Either[BaseOptumResponseError, OptumResponseSuccess]]
}
