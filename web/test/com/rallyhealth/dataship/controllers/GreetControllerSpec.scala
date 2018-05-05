package com.rallyhealth.dataship.controllers

import akka.stream.Materializer
import com.rallyhealth.dataship.database.dao.IndividualLookupDAO
import com.rallyhealth.dataship.setup.OneAppPerSuiteWithComponents
import org.scalatest.mockito.MockitoSugar.mock
import org.scalatestplus.play.PlaySpec
import play.api.mvc._
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.{ExecutionContext, Future}

class GreetControllerSpec extends PlaySpec with OneAppPerSuiteWithComponents {

  val greetRoute = "/greet"

  implicit lazy val materializer: Materializer = app.materializer

  class Fixture {
    val greetControllerImpl = new GreetControllerImpl()
  }

  s"A call to ${greetRoute}" should {
    "return greets to you" in {
      val f = new Fixture
      import f._
      val result: Future[Result] = call(greetControllerImpl.greet, FakeRequest("GET", greetRoute))
      val bodyText: String = contentAsString(result)
      assert(bodyText == "greets to you")
    }

    "return 200" in {
      val f = new Fixture
      import f._
      val action: EssentialAction = greetControllerImpl.greet
      val fakeRequest = FakeRequest("GET", greetRoute)
      val response = call(action, fakeRequest)
      assert(status(response) == 200)
    }
  }
}
