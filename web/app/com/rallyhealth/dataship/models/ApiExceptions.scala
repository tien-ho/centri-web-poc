package com.rallyhealth.dataship.models

object ApiExceptions {

  val ApiErrorCode150 = ExceptionDetail("150", "Eligibility Data not Found")

  val ApiErrorCode151 = ExceptionDetail("151", "No Eligibility Records Found as of date")

  val ApiErrorCode152 = ExceptionDetail("152", "Multiple Consumers Found")

  val ApiErrorCode153 = ExceptionDetail("153", "Event Date in the future")

  val ApiErrorCode304 = ExceptionDetail("304", "Generic Exception.")

  val ApiErrorCode306 = ExceptionDetail("306", "Search Type Not supported")

  val ApiErrorCode307 = ExceptionDetail("307", "Request Type Not supported")

  val ApiErrorCode400 = ExceptionDetail("400", "Bad request. Incorrect or Missing required data.")

  val ApiErrorCode500 = ExceptionDetail("500", "Error occurred while fulfilling request")
}
