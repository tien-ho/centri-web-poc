package com.rallyhealth.dataship.models

import com.rallyhealth.dreamliner.models.mempe.ConsumerDetails
import play.api.libs.json.{Format, Json}

case class MemberProductEligibilityResponseWrapper(
  MemberProductEligibilityResponse: MemberProductEligibilityResponse
)

case class MemberProductEligibilityResponse(
  responseHeader: ResponseHeader,
  consumerDetails: Seq[ConsumerDetails]
)

case class ResponseHeader(
  transactionId: String
)

case class ExceptionDetail(
  errorCode: String,
  exceptionMessage: String
)

object MemberProductEligibilityResponseWrapper {
  implicit val format: Format[MemberProductEligibilityResponseWrapper] = Json.format[MemberProductEligibilityResponseWrapper]
}

object MemberProductEligibilityResponse {
  implicit val format: Format[MemberProductEligibilityResponse] = Json.format[MemberProductEligibilityResponse]
}

object ResponseHeader {
  implicit val format: Format[ResponseHeader] = Json.format[ResponseHeader]
}

object ExceptionDetail {
  implicit val format: Format[ExceptionDetail] = Json.format[ExceptionDetail]
}
