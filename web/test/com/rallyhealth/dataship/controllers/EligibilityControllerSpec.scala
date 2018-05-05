package com.rallyhealth.dataship.controllers

import akka.stream.Materializer
import com.rallyhealth.dataship.configs.DatashipConfig
import com.rallyhealth.dataship.configs.DatashipConfig.Defaults
import com.rallyhealth.dataship.services.MemPEServiceImpl
import com.rallyhealth.dataship.setup.DatashipFakeApplicationFactory
import com.rallyhealth.enigma.v4.Encrypted
import com.rallyhealth.illuminati.v9.{BaseSecretConfig, SecretConfig}
import com.rallyhealth.optum.client.v8.common.model.{BaseOptumResponseError, OptumResponseAppError, OptumResponseError, OptumResponseSuccess}
import com.rallyhealth.optum.client.v8.member.WithOAuthMemberProductEligibilityClient
import com.rallyhealth.optum.client.v8.member.config.MemberProductEligibilityRallyConfig
import com.rallyhealth.spartan.v2.config.RallyConfig
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.json.Json
import play.api.mvc.{AnyContentAsJson, EssentialAction, Result, Results}
import play.api.test.Helpers._
import play.api.test.{EssentialActionCaller, FakeRequest, Writeables}

import scala.concurrent.{ExecutionContext, Future}

class Fixture extends DatashipFakeApplicationFactory with MockitoSugar {
  val rallyConfig: RallyConfig = RallyConfig(Defaults.configFile)
  val secretConfig: SecretConfig = BaseSecretConfig.fromTemplateFilename(rallyConfig, Defaults.secretTemplatePath)
  val datashipConfig: DatashipConfig = new DatashipConfig(rallyConfig, secretConfig)
  val optumConfig: MemberProductEligibilityRallyConfig = mock[MemberProductEligibilityRallyConfig]
  when(optumConfig.encryptedPayloadKey).thenReturn("encryptedPayload")

  implicit val materializer: Materializer = fakeApplication.materializer
  implicit val ec: ExecutionContext = ExecutionContext.global
  implicit val neoEncryptionService = datashipConfig.datashipApiEncryptionService

  val route = "/dataship/v1/eligibility"
  val client = mock[WithOAuthMemberProductEligibilityClient]
  val memPEService = new MemPEServiceImpl(client)
  val eligibilityController: EligibilityController = new EligibilityControllerImpl(memPEService, optumConfig)(ec, datashipConfig.datashipApiEncryptionService)
  val eligibilityJson = """{
     "MemberProductEligibilityRequest":{
        "requestHeader":{
           "applicationName":"Dreamliner",
           "transactionId":"AQbzeKYoNRTjPcarcadeuser"
        },
        "filteringAttributes":{
           "includeExtendedAttributes":true,
           "applyFilters":false
        },
        "requestDetails":{
           "requestType":"PERSONID_PLUS_CONTRACTNUMBER",
           "searchType":"MEMBER"
        },
        "idSet":{
           "personId":77346435
        },
        "consumerDetails":{
           "contractNumbers":[
              "0702863",
              "00009673"
           ]
        }
     }
  }"""

  val badEligibilityJson = """{
     "MemberProductEligibilityRequest":{
        "filteringAttributes":{
           "includeExtendedAttributes":true,
           "applyFilters":false
        },
        "requestDetails":{
           "requestType":"PERSONID_PLUS_CONTRACTNUMBER",
           "searchType":"MEMBER"
        },
        "idSet":{
           "personId":77346435
        },
        "consumerDetails":{
           "contractNumbers":[
              "0702863",
              "00009673"
           ]
        }
     }
  }"""
}

class EligibilityControllerSpec
  extends FlatSpec with DatashipFakeApplicationFactory with Matchers
  with EssentialActionCaller
  with Writeables
  with ScalaFutures
  with Results
  with MockitoSugar {

  "Call to EligibilityController with a valid request" should "return a successful response" in {
    val f = new Fixture
    import f._
    val optumResponse = OptumResponseSuccess(200, Map(), Json.toJson("""{"foo": "bar"}"""))
    when(client.findByMemberProductEligibilityRequest(any(), any())(any())).thenReturn(Future.successful(Right(optumResponse)))
    val enctypedData: Encrypted[String] = Encrypted.fromPlainString(eligibilityJson)
    val payload = Map(optumConfig.encryptedPayloadKey -> enctypedData)
    val eligibilityController: EligibilityController = new EligibilityControllerImpl(memPEService, optumConfig)
    (ec, datashipConfig.datashipApiEncryptionService)
    val action: EssentialAction = eligibilityController.getEligibility
    val fakeRequest: FakeRequest[AnyContentAsJson] = FakeRequest("POST", route).withJsonBody(Json.toJson(payload))
    val response: Future[Result] = call(action, fakeRequest)
    assert(status(response) == 200)
  }

  "Call to EligibilityController with bad Json" should "return a 400" in {
    val f = new Fixture
    import f._

    val enctypedData: Encrypted[String] = Encrypted.fromPlainString(badEligibilityJson)
    val payload = Map(optumConfig.encryptedPayloadKey -> enctypedData)
    val action: EssentialAction = eligibilityController.getEligibility
    val fakeRequest: FakeRequest[AnyContentAsJson] = FakeRequest("POST", route).withJsonBody(Json.toJson(payload))
    val response: Future[Result] = call(action, fakeRequest)
    assert(status(response) == 400)
  }

  "Call to EligibilityController that returns OptumResponseAppError" should "return the error code from Optum" in {
    val f = new Fixture
    import f._

    val errorBody = Json.toJson("""{"foo": "bar"}""")
    val errorResponse: OptumResponseAppError = OptumResponseAppError(50505, Map(), "50000", errorBody)
    when(client.findByMemberProductEligibilityRequest(any(), any())(any())).thenReturn(Future.successful(Left(errorResponse)))
    val enctypedData: Encrypted[String] = Encrypted.fromPlainString(eligibilityJson)
    val payload = Map(optumConfig.encryptedPayloadKey -> enctypedData)
    val action: EssentialAction = eligibilityController.getEligibility
    val fakeRequest: FakeRequest[AnyContentAsJson] = FakeRequest("POST", route).withJsonBody(Json.toJson(payload))
    val response: Future[Result] = call(action, fakeRequest)
    assert(status(response) == 50505)
  }

  "Call to EligibilityController that returns OptumResponseError" should "return the error code from Optum" in {
    val f = new Fixture
    import f._

    val errorBody = Json.toJson("""{"foo": "bar"}""")
    val errorResponse: OptumResponseError = OptumResponseError(101010, Map(), Some("foobar"))
    when(client.findByMemberProductEligibilityRequest(any(), any())(any())).thenReturn(Future.successful(Left(errorResponse)))
    val enctypedData: Encrypted[String] = Encrypted.fromPlainString(eligibilityJson)
    val payload = Map(optumConfig.encryptedPayloadKey -> enctypedData)
    val action: EssentialAction = eligibilityController.getEligibility
    val fakeRequest: FakeRequest[AnyContentAsJson] = FakeRequest("POST", route).withJsonBody(Json.toJson(payload))
    val response: Future[Result] = call(action, fakeRequest)
    assert(status(response) == 101010)
  }

  "Call to EligibilityController that returns BaseOptumResponseError" should "return a 500" in {
    val f = new Fixture
    import f._

    val errorResponse = new BaseOptumResponseError {}
    when(client.findByMemberProductEligibilityRequest(any(), any())(any())).thenReturn(Future.successful(Left(errorResponse)))
    val enctypedData: Encrypted[String] = Encrypted.fromPlainString(eligibilityJson)
    val payload = Map(optumConfig.encryptedPayloadKey -> enctypedData)
    val action: EssentialAction = eligibilityController.getEligibility
    val fakeRequest: FakeRequest[AnyContentAsJson] = FakeRequest("POST", route).withJsonBody(Json.toJson(payload))
    val response: Future[Result] = call(action, fakeRequest)
    assert(status(response) == 500)
  }

  "Call to EligibilityController with an empty payload" should "error out" in {
    val f = new Fixture
    import f._

    val payload = ""
    val action: EssentialAction = eligibilityController.getEligibility
    val fakeRequest: FakeRequest[AnyContentAsJson] = FakeRequest("POST", route).withJsonBody(Json.toJson(payload))
    val response: Future[Result] = call(action, fakeRequest)
  }
}
