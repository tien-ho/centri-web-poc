package com.rallyhealth.dataship.reader.setup

import com.rallyhealth.core.database.postgres.v2.evolutions.InMemoryPlayDB
import com.rallyhealth.dataship.database.modules.DatabaseModule
import com.rallyhealth.dataship.reader.modules.{DatashipReaderApplicationLoader, DatashipReaderModuleFromContext}
import com.rallyhealth.spartan.v2.config.{MemoryRallyConfig, RallyConfig}
import org.scalatestplus.play.FakeApplicationFactory
import play.api.{Application, ApplicationLoader, Environment, Mode}

trait DatashipFakeApplicationFactory extends FakeApplicationFactory {
  def testContext: ApplicationLoader.Context = {
    val classLoader = ApplicationLoader.getClass.getClassLoader
    val env = new Environment(new java.io.File("."), classLoader, Mode.Test)
    ApplicationLoader.createContext(env)
  }

  lazy val datashipApplicationLoader = new DatashipReaderApplicationLoader

  lazy val datashipReaderModuleFromTestContext: DatashipReaderModuleFromContext = {
    new DatashipReaderModuleFromContext(testContext) {
      // Avoid applying evolutions in tests
      override def applyEvolutions(): Unit = {}
      override lazy val rallyConfig: RallyConfig = new MemoryRallyConfig(
        Map(
          s"kafka.key.decryption.location" -> "conf/localhost_pkcs8.pem",
          s"kafka.ssl.keystore.location" -> "conf/localhost_keystore.jks",
          s"kafka.ssl.truststore.location" -> "conf/localhost_truststore.jks"
        )
      )
      override lazy val databaseModule: DatabaseModule = new DatabaseModule(rallyConfig, secretConfig) with InMemoryPlayDB
    }
  }

  override def fakeApplication(): Application = datashipReaderModuleFromTestContext.application
}
