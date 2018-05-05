package com.rallyhealth.dataship.configs

import javax.xml.bind.DatatypeConverter

import com.rallyhealth.dataship.configs.DatashipConfig.Defaults
import com.rallyhealth.enigma.v4.{GCMCipherProvider, NeoEncryptionService, NeoEncryptionServiceImpl}
import com.rallyhealth.illuminati.v9.{SecretConfig, SecretValue}
import com.rallyhealth.spartan.v2.config.RallyConfig
import org.bouncycastle.crypto.params.KeyParameter

case class DatashipConfig(
  rallyConfig: RallyConfig,
  secretConfig: SecretConfig) {

  lazy val loggingSecureEnabled: Boolean = rallyConfig.get("dataship.secure.logging.secureenabled", "false").toString.toBoolean
  lazy val loggingSecureLinkEnabled: Boolean = rallyConfig.get("dataship.secure.logging.securelinkenabled", "false").toString.toBoolean
  lazy val loggingEncryptKey: String = secretConfig.get("dataship.secure.logging.encryptkey", Defaults.loggingEncryptKey)
  lazy val playSecret: String = secretConfig.get("dataship.play.secret", Defaults.playSecret)
  lazy val rqStatsPrefix: String = rallyConfig.get("dataship.rqstats.prefix", "dataship")
  lazy val oauthClientId: String = secretConfig.get("dataship.optum.oauth.client.id", Defaults.oauthClientId)
  lazy val oauthClientSecret: String = secretConfig.get("dataship.optum.oauth.client.secret", Defaults.oauthClientSecret)
  lazy val serviceEncryptKey: SecretValue = secretConfig.get("dataship.encrypt.key.service", Defaults.defaultKeyValue)

  lazy val keyParameter: KeyParameter = {
    val encryptKey = DatatypeConverter.parseHexBinary(serviceEncryptKey.value.replaceAll("\\s", ""))
    new KeyParameter(encryptKey)
  }

  lazy val datashipApiEncryptionService: NeoEncryptionService = {
    val cipherProvider = new GCMCipherProvider(keyParameter)
    new NeoEncryptionServiceImpl(cipherProvider)
  }
}

object DatashipConfig {
  object Defaults {
    val loggingEncryptKey = "bf 2c 84 20 df e4 e5 cf 3a 82 07 7d ba 1d fe 59 f5 9e 53 de a1 a6 9c ff 65 3e 0c 60 d0 b0 08 78"
    val secretTemplatePath = "/illuminati-templates/dataship.json"
    val configFile = "dataship.conf"
    val playSecret = "Q>y09kEj6RmU;Rv/8wm[?H4qU<?DI7FehfGr9=F84dH`i:BdhaxK1yM17/avWYsa"
    val oauthClientId = "l7xx21370679142b451296b04cc6bb781be8"
    val oauthClientSecret = "08b508da6b7d44bc8cd4da1a3525d267"
    val defaultKeyValue = "A3 5D 53 39 2A 05 31 86 40 62 54 0A 32 84 97 B5 98 A2 65 E0 70 89 AA EC E5 8E 60 71 39 87 DE 17"
  }

  val serviceName: String = "Dataship"
}
