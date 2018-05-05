package com.rallyhealth.dataship.modules

import com.rallyhealth.dataship.configs.DatashipConfig
import com.rallyhealth.dataship.configs.DatashipConfig.Defaults
import com.rallyhealth.dataship.database.modules.DatabaseModule
import com.rallyhealth.illuminati.v9.{BaseSecretConfig, SecretConfig}
import com.rallyhealth.interceptor.kamon.CorrelationIdServerInterceptor
import com.rallyhealth.interceptor.play25.server.InterceptorImplicits._
import com.rallyhealth.interceptor.play25.server.InterceptorPlayFilter
import com.rallyhealth.interceptor.play25.server.InterceptorPlayFilter.PlayServerInterceptor
import com.rallyhealth.interceptor.stats.{ClientStats, HttpStatsV4Interceptor}
import com.rallyhealth.interceptor.v0.InterceptorImplicits._
import com.rallyhealth.spartan.v2.config.RallyConfig
import com.rallyhealth.stats.v4.Stats
import com.softwaremill.macwire._
import play.api.ApplicationLoader.Context
import play.api._
import play.api.cache.{CacheApi, EhCacheComponents}
import play.api.db.DBApi
import play.api.db.evolutions.EvolutionsComponents
import play.api.libs.ws.ahc.AhcWSComponents
import play.api.mvc.EssentialFilter
import play.api.routing.Router
import router.Routes

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Random

class DatashipApplicationLoader extends ApplicationLoader {

  def createComponents(context: Context): DatashipModuleFromContext = {
    // $COVERAGE-OFF$
    // In tests, this method should be overriden to create the component with in memory DB.
    new DatashipModuleFromContext(context)
    // $COVERAGE-ON$
  }

  override def load(context: Context): Application = {
    /*
     * When using a custom application loader that does not extend the
     * default GuiceApplicationLoader, the LoggerConfigurator needs to be manually
     * invoked to pick up your custom configuration.
     */
    LoggerConfigurator(context.environment.classLoader).foreach {
      _.configure(context.environment)
    }

    val dataship = createComponents(context)
    dataship.application
  }
}

class DatashipModuleFromContext(context: Context)
  extends BuiltInComponentsFromContext(context)
  with AhcWSComponents
  with EhCacheComponents
  with EvolutionsComponents {

  implicit val executionContext: ExecutionContext = actorSystem.dispatcher

  override lazy val httpFilters: Seq[EssentialFilter] = Seq(
    playServerFilter
  )

  lazy val playServerFilter: InterceptorPlayFilter = {

    val correlationIds = CorrelationIdServerInterceptor(DatashipConfig.serviceName)

    val stats: PlayServerInterceptor = new HttpStatsV4Interceptor(
      ClientStats(Stats, DatashipConfig.serviceName.toLowerCase())
    )

    val interceptor = {
      correlationIds.wrap {
        stats
      }
    }
    new InterceptorPlayFilter(interceptor)
  }

  // Configs
  lazy val rallyConfig: RallyConfig = RallyConfig(Defaults.configFile)
  lazy val secretConfig: SecretConfig = BaseSecretConfig.fromTemplateFilename(rallyConfig, Defaults.secretTemplatePath)

  // Modules
  lazy val configModule: ConfigModule = wire[ConfigModuleImpl]
  lazy val serviceModule: ServiceModule = wire[ServiceModuleImpl]
  lazy val databaseModule: DatabaseModule = wire[DatabaseModule]
  lazy val controllerModule: ControllerModule = wire[ControllerModuleImpl]
  implicit lazy val cache: CacheApi = cacheApi(s"play-${Random.nextInt(20000)}")

  lazy val memPEClientModule: MemPEClientModule = wire[MemPEClientModuleImpl]
  lazy val rqClientModule: RqClientModule = wire[RqClientModuleImpl]

  // Evolutions
  lazy val dbApi: DBApi = databaseModule.dbApi

  // Ensure to clean up all connections when shutting down the service
  applicationLifecycle.addStopHook(() => Future.successful(databaseModule.hikariDataSource.close()))

  // $COVERAGE-OFF$
  def applyEvolutions(): Unit = {
    applicationEvolutions
  }

  /**
    * Since everything is lazy we need to run it.
    * @see https://www.playframework.com/documentation/2.5.x/Evolutions#Running-evolutions-using-compile-time-DI
    */
  applyEvolutions()
  // $COVERAGE-ON$

  lazy val opsRoutes: opsStatic.Routes = {
    wire[opsStatic.Routes]
  }

  lazy val router: Router = {
    lazy val prefix = "/"
    wire[Routes]
  }

  override lazy val configuration: Configuration = {
    val playSecret = Configuration("play.crypto.secret" -> configModule.datashipConfig.playSecret)
    context.initialConfiguration ++ playSecret
  }
}
