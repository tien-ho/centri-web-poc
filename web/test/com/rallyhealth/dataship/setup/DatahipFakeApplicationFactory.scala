package com.rallyhealth.dataship.setup

import com.rallyhealth.core.database.postgres.v2.evolutions.InMemoryPlayDB
import com.rallyhealth.dataship.database.modules.DatabaseModule
import com.rallyhealth.dataship.modules.{DatashipApplicationLoader, DatashipModuleFromContext}
import org.scalatestplus.play.FakeApplicationFactory
import play.api.{Application, ApplicationLoader, Environment, Mode}

trait DatashipFakeApplicationFactory extends FakeApplicationFactory {
  def testContext: ApplicationLoader.Context = {
    val classLoader = ApplicationLoader.getClass.getClassLoader
    val env = new Environment(new java.io.File("."), classLoader, Mode.Test)
    ApplicationLoader.createContext(env)
  }

  lazy val datashipApplicationLoader = new DatashipApplicationLoader {
    override def createComponents(context: ApplicationLoader.Context) = {
      datashipModuleFromTestContext
    }
  }

  lazy val datashipModuleFromTestContext: DatashipModuleFromContext = {
    new DatashipModuleFromContext(testContext) {
      // Avoid applying evolutions in tests
      override def applyEvolutions() = {}

      // Use InMemoryDB for tests
      override lazy val databaseModule: DatabaseModule = new DatabaseModule(rallyConfig, secretConfig) with InMemoryPlayDB
    }
  }

  def loadFakeApplication: Application = datashipApplicationLoader.load(testContext)

  override def fakeApplication(): Application = loadFakeApplication
}
