package com.rallyhealth.dataship.controllers

import com.rallyhealth.correlation.CorrelationId
import com.rallyhealth.dataship.configs.DatashipConfig.Defaults
import com.rallyhealth.dataship.services.MemPEService
import com.rallyhealth.enigma.v4.logging.SecureLogger._
import com.rallyhealth.enigma.v4.{Encrypted, NeoEncryptionService}
import com.rallyhealth.optum.client.v8.common.OptumClientConfig
import com.rallyhealth.optum.client.v8.common.model.{BaseOptumResponseError, OptumResponseAppError, OptumResponseError}
import com.rallyhealth.optum.client.v8.member.ws.MemPERequestBodyBuilder.MemberProductEligibilityRequest
import com.rallyhealth.spartan.v2.util.logging.DefaultLogger
import play.api.libs.json._
import play.api.mvc.{Action, EssentialAction}

import scala.concurrent.{ExecutionContext, Future}

class HealthCheckControllerImpl(memPEService: MemPEService,
                                optumClientConfig: OptumClientConfig)(implicit ec: ExecutionContext, neoEncryptionService: NeoEncryptionService) extends HealthCheckController with DefaultLogger {


    def getHealthCheckStatus: EssentialAction = Action.async(parse.json) { request =>
        logger.info(s"Received request ${request.body}")
        val encryptedPayload: JsLookupResult = Json.toJson(request.body) \ optumClientConfig.encryptedPayloadKey
        val payload: String = decryptPayload(encryptedPayload.getOrElse(
            throw new IllegalArgumentException("Empty JSON Payload")).as[String])
        val jsonPayload: JsValue = Json.parse(payload)

        val memberProductEligibilityRequest = jsonPayload \ "MemberProductEligibilityRequest"

        memberProductEligibilityRequest.validate[MemberProductEligibilityRequest] match {
            case JsSuccess(body, _) =>
                memPEService.memberProductEligibility(body, request.headers.get(CorrelationId.HeaderName)).flatMap {
                    case Right(success) =>
                        Future.successful(Status(success.httpStatus)(encryptPayload(success.body.toString)))
                    case Left(optumResponseAppError: OptumResponseAppError) =>
                        Future.successful(Status(optumResponseAppError.httpStatus)(encryptPayload(optumResponseAppError.body.toString)))
                    case Left(optumResponseError: OptumResponseError) =>
                        Future.successful(Status(optumResponseError.httpStatus)(optumResponseError.body.getOrElse("An error occured")))
                    case Left(error: BaseOptumResponseError) =>
                        Future.successful(InternalServerError(s"An error occured ${error.toString}"))
                }
            case JsError(error) =>
                Future.successful(BadRequest(error.toString))
        }
    }

    private[controllers] def decryptPayload(payload: String): String = {
        logger.debug("Encrypted Request ", payload)
        val decryptedData = neoEncryptionService.decryptString(payload)
        logger.secureInfo(s"Decrypted payload", decryptedData.replaceAll("\n", ""))
        decryptedData
    }

    private[controllers] def encryptPayload(payload: String): JsValue = {
        logger.secureInfo("Encrypting", payload)
        val encryptedData: Encrypted[String] = Encrypted.fromPlainString(payload)
        logger.debug(s"Encrypted Response ", encryptedData)
        Json.toJson(Map(optumClientConfig.encryptedPayloadKey -> encryptedData))
    }


    override def healthStatus = Action {
            implicit request =>
                Ok("It's all good!!!")
    }


}


//
//class HealthCheckControllerImpl extends HealthCheckController with DefaultLogger {
//
//    override def healthStatus = Action {
//        implicit request =>
//            Ok("It's all good!!!")
//    }
//}
