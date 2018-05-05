package com.rallyhealth.dataship.modules

import com.rallyhealth.enigma.v4.NeoEncryptionService
import com.rallyhealth.optum.client.v8.auth.OAuthClient
import com.rallyhealth.optum.client.v8.auth.model.OptumAccessTokenRequest
import com.rallyhealth.optum.client.v8.auth.ws.{CachedOptumOauthClient, WSOptumOauthClient}
import com.rallyhealth.optum.client.v8.member.ws.{WSMemberProductEligibilityClient, WSWithOAuthMemberProductEligibilityClient}
import com.rallyhealth.optum.client.v8.member.{MemberProductEligibilityClient, WithOAuthMemberProductEligibilityClient}
import com.softwaremill.macwire.{Module, wire}
import play.api.cache.CacheApi

import scala.concurrent.ExecutionContext

/**
  * Module for the MemPeClient
  */
@Module
trait MemPEClientModule {

  def withOAuthMemberProductEligibilityClient: WithOAuthMemberProductEligibilityClient
  def oAuthClient: OAuthClient
  def memPeClient: MemberProductEligibilityClient
  def optumAccessTokenRequest: OptumAccessTokenRequest
}

/**
  * Definitions of the MemPEClientModule and its dependencies
  */
case class MemPEClientModuleImpl(
  configModule: ConfigModule,
  rqClientModule: RqClientModule
)(implicit ec: ExecutionContext, cacheApi: CacheApi) extends MemPEClientModule {

  override lazy val withOAuthMemberProductEligibilityClient = wire[WSWithOAuthMemberProductEligibilityClient]
  override lazy val oAuthClient = {
    val client: WSOptumOauthClient = new WSOptumOauthClient(configModule.memPeClientConfig, optumAccessTokenRequest, rqClientModule.rqClient)
    new CachedOptumOauthClient(client, cacheApi)
  }

  //This is to satisfy the need for a None NeoEncryptionService in WSMemberProductEligibilityClient
  val emptyEncryptionService: Option[NeoEncryptionService] = None
  override lazy val memPeClient = wire[WSMemberProductEligibilityClient]

  override lazy val optumAccessTokenRequest =
    new OptumAccessTokenRequest(configModule.datashipConfig.oauthClientId, configModule.datashipConfig.oauthClientSecret)
}
