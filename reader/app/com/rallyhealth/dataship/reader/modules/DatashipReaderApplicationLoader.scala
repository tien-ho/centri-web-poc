package com.rallyhealth.dataship.reader.modules

import akka.actor.{ActorSystem, Scheduler}
import com.rallyhealth.dataship.database.modules.DatabaseModule
import com.rallyhealth.dataship.database.services.{DatashipDatabaseHmacService, DatashipDatabaseNeoEncryptionService}
import com.rallyhealth.dataship.reader.configs.DatashipReaderConfig
import com.rallyhealth.dataship.reader.crypto.KeyDecryptor
import com.rallyhealth.illuminati.v9.SecretConfig
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
import play.api.db.DBApi
import play.api.db.evolutions.EvolutionsComponents
import play.api.libs.concurrent.ActorSystemProvider
import play.api.libs.ws.ahc.AhcWSComponents
import play.api.mvc.EssentialFilter
import play.api.routing.Router
import router.Routes

import scala.concurrent.{ExecutionContext, Future}

class DatashipReaderApplicationLoader extends ApplicationLoader {

  override def load(context: Context): Application = {
    /*
     * When using a custom application loader that does not extend the
     * default GuiceApplicationLoader, the LoggerConfigurator needs to be manually
     * invoked to pick up your custom configuration.
     */
    // $COVERAGE-OFF$
    LoggerConfigurator(context.environment.classLoader).foreach {
      _.configure(context.environment)
    }
    val dataship = new DatashipReaderModuleFromContext(context)
    dataship.application
    // $COVERAGE-ON$
  }
}

class DatashipReaderModuleFromContext(context: Context)
  extends BuiltInComponentsFromContext(context)
  with EvolutionsComponents
  with AhcWSComponents {

  implicit val executionContext: ExecutionContext = actorSystem.dispatcher

  override lazy val httpFilters: Seq[EssentialFilter] = Seq(
    playServerFilter
  )

  lazy val playServerFilter: InterceptorPlayFilter = {

    val correlationIds = CorrelationIdServerInterceptor(DatashipReaderConfig.serviceName)

    val stats: PlayServerInterceptor = new HttpStatsV4Interceptor(
      ClientStats(Stats, DatashipReaderConfig.serviceName.toLowerCase())
    )

    val interceptor = {
      correlationIds.wrap {
        stats
      }
    }
    new InterceptorPlayFilter(interceptor)
  }

  val kafkaConsumerActorSystem: ActorSystem = new ActorSystemProvider(environment, configuration, applicationLifecycle).get
  val kafkaConsumerScheduler: Scheduler = kafkaConsumerActorSystem.scheduler
  val maybeKeyDecryptor: Option[KeyDecryptor] = None

  lazy val rallyConfig: RallyConfig = DatashipReaderConfig.rallyConfig
  lazy val secretConfig: SecretConfig = DatashipReaderConfig.secretConfig

  lazy val configModule: ConfigModule = wire[ConfigModuleImpl]
  lazy val databaseModule: DatabaseModule = wire[DatabaseModule]

  lazy val serviceModule: ServiceModule = wire[ServiceModule]
  lazy val controllerModule: ControllerModule = wire[ControllerModuleImpl]

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

  // $COVERAGE-OFF$
  if (context.environment.mode != Mode.Test) {
    implicit lazy val encryptionService = DatashipDatabaseNeoEncryptionService(configModule.datashipConfig.databaseEncryptionService)
    implicit lazy val hmacService = DatashipDatabaseHmacService(configModule.datashipConfig.databaseHmacService)

    serviceModule.rallyKafkaConsumersService.initialize(serviceModule.dataLocalizationService)
  }
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
    val kafkaKeystorePasswordSecret = Configuration("kafka.ssl.keystore.password" -> configModule.datashipConfig.kafkaSSLKeystorePassword)
    val kafkaTruststorePasswordSecret = Configuration("kafka.ssl.truststore.password" -> configModule.datashipConfig.kafkaSSLTruststorePassword)
    val kafkaKeyPasswordSecret = Configuration("kafka.ssl.key.password" -> configModule.datashipConfig.kafkaSSLKeyPassword)
    context.initialConfiguration ++ playSecret ++ kafkaKeyPasswordSecret ++ kafkaKeystorePasswordSecret ++ kafkaTruststorePasswordSecret
  }
}
