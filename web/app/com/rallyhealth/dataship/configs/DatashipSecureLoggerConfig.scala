package com.rallyhealth.dataship.configs

import javax.xml.bind.DatatypeConverter

import com.rallyhealth.enigma.v4.logging.SecureLoggerConfig
import org.bouncycastle.crypto.params.KeyParameter

/**
  * Implements [[SecureLoggerConfig]], so that this can replace the underlying config mechanism for the secure
  * logger, in order to pull its secrets from Vault.
  *
  * The pattern we're using here is getting the configuration values that lib-enigma needs for the secure logger,
  * so we can set lib-enigma to use them, as opposed to lib-enigma using the config values that it fetches itself.
  */
class DatashipSecureLoggerConfig(
  datashipConfig: DatashipConfig
) extends SecureLoggerConfig {

  override val secureEnabled: Boolean = datashipConfig.loggingSecureEnabled
  override val secureLinkEnabled: Boolean = datashipConfig.loggingSecureLinkEnabled
  override val secureKeyParameter: KeyParameter = {
    /** Key that is used to encrypt log data */
    val encryptKey = DatatypeConverter.parseHexBinary(
      datashipConfig.loggingEncryptKey.replaceAll("\\s", "")
    )
    new KeyParameter(encryptKey)
  }
}
