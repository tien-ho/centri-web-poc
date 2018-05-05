package com.rallyhealth.dataship.setup

import org.scalatest.TestSuite
import org.scalatestplus.play.{BaseOneAppPerSuite, FakeApplicationFactory}

/**
  * Extend this trait if one application per suite is needed.
  *
  * For most of the test if running application is need use this trait.
  * Because application is not storing any state, one application per suite is enough.
  */
trait OneAppPerSuiteWithComponents
  extends BaseOneAppPerSuite with DatashipFakeApplicationFactory {
  this: TestSuite with FakeApplicationFactory =>
}
