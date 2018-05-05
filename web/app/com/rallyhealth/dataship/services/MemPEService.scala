package com.rallyhealth.dataship.services

import java.time.Instant

import com.rallyhealth.dataship.util.{DatashipStats, StatsConstants}
import com.rallyhealth.optum.client.v8.common.model.{BaseOptumResponseError, OptumResponseSuccess}
import com.rallyhealth.optum.client.v8.member.WithOAuthMemberProductEligibilityClient
import com.rallyhealth.optum.client.v8.member.ws.MemPERequestBodyBuilder.MemberProductEligibilityRequest
import com.rallyhealth.spartan.v2.util.logging.DefaultLogger

import scala.concurrent.{ExecutionContext, Future}

trait MemPEService {

  /**
    * Calls the Optum MemPE API
    *
    * @param request The MemberProductEligibilityRequest coming in from the client
    * @return Returns either an ErrorResponse from MemPE or a model containing a user's EligibilityRecords
    */
  def memberProductEligibility(
    request: MemberProductEligibilityRequest,
    correlationId: Option[String])(implicit ec: ExecutionContext): Future[Either[BaseOptumResponseError, OptumResponseSuccess]]
}

class MemPEServiceImpl(
  memPEClient: WithOAuthMemberProductEligibilityClient
) extends MemPEService
  with DefaultLogger
  with DatashipStats {

  override def statPath: Seq[String] = StatsConstants.mempeservice

  def memberProductEligibility(
    request: MemberProductEligibilityRequest,
    correlationId: Option[String])(implicit ec: ExecutionContext): Future[Either[BaseOptumResponseError, OptumResponseSuccess]] = {

    val startTime = Instant.now().toEpochMilli

    memPEClient.findByMemberProductEligibilityRequest(request, correlationId) map {
      case Right(success) =>
        emitSuccess(Some(startTime))
        Right(success)
      case Left(failure) =>
        emitError(Some(startTime))
        Left(failure)
    }
  }
}
