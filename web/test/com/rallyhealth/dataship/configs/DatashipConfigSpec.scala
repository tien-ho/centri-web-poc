package com.rallyhealth.dataship.configs

import com.rallyhealth.dataship.setup.DatashipFakeApplicationFactory
import org.scalatest.FunSpec

import scala.concurrent.ExecutionContext

class DatashipConfigSpec extends FunSpec with DatashipFakeApplicationFactory {

  val datashipContext = datashipModuleFromTestContext
  val datashipConfig: DatashipConfig = datashipContext.configModule.datashipConfig

  it("should add custom play crypto secret in play configuration") {
    assert(datashipContext.configuration.getString("play.crypto.secret").isDefined)
    assert(
      datashipContext.configuration.getString("play.crypto.secret").get ==
        "Q>y09kEj6RmU;Rv/8wm[?H4qU<?DI7FehfGr9=F84dH`i:BdhaxK1yM17/avWYsa"
    )
  }

  it("should get all the Dataship config") {
    assert(datashipConfig.loggingSecureEnabled == false)
    assert(datashipConfig.loggingSecureLinkEnabled == false)
    assert(
      datashipConfig.loggingEncryptKey ==
        "bf 2c 84 20 df e4 e5 cf 3a 82 07 7d ba 1d fe 59 f5 9e 53 de a1 a6 9c ff 65 3e 0c 60 d0 b0 08 78"
    )
    assert(datashipConfig.playSecret == "Q>y09kEj6RmU;Rv/8wm[?H4qU<?DI7FehfGr9=F84dH`i:BdhaxK1yM17/avWYsa")
  }

  it("should set execution context") {
    assert(datashipContext.executionContext.isInstanceOf[ExecutionContext])
  }
}
