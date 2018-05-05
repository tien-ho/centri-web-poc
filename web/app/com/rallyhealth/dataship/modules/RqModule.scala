package com.rallyhealth.dataship.modules

import com.rallyhealth.dataship.configs.DatashipConfig
import com.rallyhealth.rq.v1.logging.LoggingRqBackend
import com.rallyhealth.rq.v1.play25.Play25RqBackend
import com.rallyhealth.rq.v1.stats.StatsRqBackend
import com.rallyhealth.rq.v1.RqClient
import com.rallyhealth.stats.v3.{HttpClientStats, KeyPrefixedStats, Stats}
import com.softwaremill.macwire._
import org.slf4j.LoggerFactory
import play.api.libs.ws.WSClient

import scala.concurrent.ExecutionContext

/**
  * Module for the RqClient which is the Play2.5 compatible RallyWsClient
  */
@Module
trait RqClientModule {
  def rqClient: RqClient
}

case class RqClientModuleImpl(
  wsClient: WSClient,
  config: DatashipConfig
)(implicit ec: ExecutionContext) extends RqClientModule {

  val stats = HttpClientStats.build(new KeyPrefixedStats(Stats, config.rqStatsPrefix), LoggerFactory.getLogger(config.rqStatsPrefix))
  val playBkEnd = new StatsRqBackend(stats)(new LoggingRqBackend("dataship", 1000)(new Play25RqBackend(wsClient)))
  override val rqClient = new RqClient(playBkEnd)
}
