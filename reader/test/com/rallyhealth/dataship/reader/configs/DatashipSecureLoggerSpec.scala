package com.rallyhealth.dataship.reader.configs

import com.rallyhealth.dataship.reader.setup.DatashipFakeApplicationFactory
import org.bouncycastle.crypto.params.KeyParameter
import org.scalatest.FunSpec

class DatashipSecureLoggerConfigSpec
  extends FunSpec
  with DatashipFakeApplicationFactory {
  val datashipContext = datashipReaderModuleFromTestContext
  val secureLoggerConfig = datashipContext.configModule.secureLoggerConfig

  it("should use the default configured values") {
    assert(!secureLoggerConfig.secureEnabled)
    assert(!secureLoggerConfig.secureLinkEnabled)
    assert(secureLoggerConfig.secureKeyParameter.isInstanceOf[KeyParameter])
  }
}
