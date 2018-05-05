package com.rallyhealth.dataship.reader.configs

import javax.xml.bind.DatatypeConverter

import com.rallyhealth.dataship.reader.configs.DatashipReaderConfig.Defaults
import com.rallyhealth.enigma.v4._
import com.rallyhealth.illuminati.v9.{BaseSecretConfig, SecretConfig, SecretValue}
import com.rallyhealth.spartan.v2.config.RallyConfig
import org.bouncycastle.crypto.params.KeyParameter

/**
  * Class to hold the configurations from RallyConfig (non-secret) and SecretConfig (secrets)
  *
  */
case class DatashipReaderConfig(
  rallyConfig: RallyConfig,
  secretConfig: SecretConfig
) {
  lazy val loggingSecureEnabled: Boolean = rallyConfig.get("dataship.secure.logging.secureenabled", "false").toBoolean
  lazy val loggingSecureLinkEnabled: Boolean = rallyConfig.get("dataship.secure.logging.securelinkenabled", "false").toBoolean
  lazy val loggingEncryptKey: String = secretConfig.get("dataship.secure.logging.encryptkey", Defaults.loggingEncryptKey)
  lazy val playSecret: String = secretConfig.get("dataship.play.secret", Defaults.playSecret)
  lazy val dataLocalizationEnabled: Boolean = rallyConfig.get("dataship.data.localization.enabled", Defaults.localizationEnabled).toBoolean
  lazy val dataLocalizationMaxWaitTimeSec: Int = rallyConfig.get("dataship.data.localization.max.waittime.seconds", Defaults.localizationMaxWaittime).toInt

  /**
    * Kafka related configs
    */
  lazy val kafkaEncryptedMessagesFlag: Boolean = rallyConfig.get("kafka.encrypted.messages.flag", Defaults.kafkaEncryptedFlag).toBoolean
  lazy val numKafkaReaders: Int = rallyConfig.get("kafka.numReaders", Defaults.numKafkaReaders).toInt
  lazy val kafkaTopic: String = rallyConfig.get("kafka.topic", Defaults.kafkaTopic)
  lazy val kafkaGroupId: String = rallyConfig.get("kafka.group.id", Defaults.kafkaGroupId)
  lazy val kafkaBroker: String = rallyConfig.get("kafka.broker.host", Defaults.kafkaBrokerHost) + ":" +
    rallyConfig.get("kafka.broker.port", Defaults.kafkaBrokerPort)
  lazy val kafkaZookeeper: String = rallyConfig.get("kafka.zookeeper.host", Defaults.kafkaZookeeperHost) + ":" +
    rallyConfig.get("kafka.zookeeper.port", Defaults.kafkaZookeeperPort)
  lazy val kafkaSchemaRepo: String = rallyConfig.get("kafka.schemaRepo.protocol", Defaults.kafkaSchemaRepoProtocol) + "://" +
    rallyConfig.get("kafka.schemaRepo.host", Defaults.kafkaSchemaRepoHost) + ":" +
    rallyConfig.get("kafka.schemaRepo.port", Defaults.kafkaSchemaRepoPort)
  lazy val kafkaSecurityProtocol: String = rallyConfig.get("kafka.security.protocol", Defaults.kafkaSecurityProtocol)
  lazy val kafkaSSLKeystoreLocation: String = rallyConfig.get("kafka.ssl.keystore.location", Defaults.kafkaSSLKeystoreLocation)
  lazy val kafkaSSLKeystorePassword: String = secretConfig.get("dataship.kafka.ssl.keystore.password", Defaults.kafkaSSLKeystorePassword)
  lazy val kafkaSSLTruststoreLocation: String = rallyConfig.get("kafka.ssl.truststore.location", Defaults.kafkaSSLTruststoreLocation)
  lazy val kafkaSSLTruststorePassword: String = secretConfig.get("dataship.kafka.ssl.truststore.password", Defaults.kafkaSSLTruststorePassword)
  lazy val kafkaSSLKeyPassword: String = secretConfig.get("dataship.kafka.ssl.key.password", Defaults.kafkaSSLKeyPassword)

  lazy val kafkaReadFrequency: Int = rallyConfig.get("kafka.read.frequency", Defaults.kafkaReadFrequency).toInt
  lazy val kafkaAutoOffsetReset: String = rallyConfig.get("kafka.auto.offset.reset", Defaults.kafkaAutoOffsetReset)
  lazy val kafkaPollPeriod: Int = rallyConfig.get("kafka.poll.period", Defaults.kafkaPollPeriod).toInt
  lazy val kafkaMaxPollRecords: String = rallyConfig.get("kafka.max.poll.records", Defaults.kafkaMaxPollRecords)
  lazy val kafkaSessionTimeoutMs: String = rallyConfig.get("kafka.session.timeout.ms", Defaults.kafkaSessionTimeoutMs)
  lazy val kafkaKeyDecryptionKeyLocation: String = rallyConfig.get("kafka.key.decryption.location", Defaults.kafkaKeyDecryptionLocation)
  lazy val kafkaCEKCacheMaxSize: Int = rallyConfig.get("kafka.cek.cache.maxsize", Defaults.kafkaCEKCacheMaxSize).toInt

  /**
    * Encryption related configs
    */
  lazy val serviceEncryptDatabaseKey: SecretValue = secretConfig.get("dataship.encrypt.database.key.service", Defaults.defaultDatabaseKeyValue)
  lazy val databaseKeyParameter: KeyParameter = {
    val encryptKey = DatatypeConverter.parseHexBinary(serviceEncryptDatabaseKey.value.replaceAll("\\s", ""))
    new KeyParameter(encryptKey)
  }
  lazy val databaseEncryptionService: NeoEncryptionService = {
    val cipherProvider = new GCMCipherProvider(databaseKeyParameter)
    new NeoEncryptionServiceImpl(cipherProvider)
  }

  /**
    * Hashing related configs
    */
  lazy val serviceHashKey: SecretValue = secretConfig.get("dataship.hash.key.service", Defaults.hashKeyValue)
  lazy val databaseHmacService: HMacService = {
    val hashKey = DatatypeConverter.parseHexBinary(serviceHashKey.value.replaceAll("\\s", ""))
    val hashKeyParameter = new KeyParameter(hashKey)
    new Sha3HMacService(hashKeyParameter)
  }
}

object DatashipReaderConfig {
  object Defaults {
    val defaultDatabaseKeyValue = "49 97 ED 33 72 81 A5 AA 3A 04 45 D5 87 47 B1 DE BD CF 1B AB 5D E9 36 7B 90 03 C2 8F 09 AB BC 92"
    val hashKeyValue = "57 EE 46 C6 EB D6 4C EC E9 C4 71 C7 C7 5E 53 1E 62 0F B6 1B 8E 28 47 4E AA 55 90 A7 33 E8 FC 09"
    val loggingEncryptKey = "bf 2c 84 20 df e4 e5 cf 3a 82 07 7d ba 1d fe 59 f5 9e 53 de a1 a6 9c ff 65 3e 0c 60 d0 b0 08 78"
    val secretTemplatePath = "/illuminati-templates/dataship.json"
    val configFile = "conf/dataship.conf"
    val playSecret = "Q>y09kEj6RmU;Rv/8wm[?H4qU<?DI7FehfGr9=F84dH`i:BdhaxK1yM17/avWYsa"

    val kafkaKeyDecryptionLocation = "./reader/conf/localhost_pkcs8.pem"
    val kafkaCEKCacheMaxSize = "100"
    val kafkaEncryptedFlag = "true"
    val numKafkaReaders = "1"
    val kafkaGroupId = "RallyConsumerGroupLocal"
    val kafkaTopic = "DE_STREAMS.ctc.cdc.rally_encrypted.eligibility.v1"
    val kafkaBrokerHost = "lab-kafka-elb-401925143.us-east-1.elb.amazonaws.com"
    val kafkaBrokerPort = "443"
    val kafkaZookeeperHost = "localhost"
    val kafkaZookeeperPort = "2181"
    val kafkaSchemaRepoProtocol = "https"

    // Using dev-core to connect even locally as a default because setting up a local https schema-registry isn't easy
    // DS-17 created to address configuring schema registry in Maestro
    val kafkaSchemaRepoHost = "core-rally-schema-registry.dev-core.werally.in"
    val kafkaSchemaRepoPort = "443"
    val kafkaSecurityProtocol = "SSL"
    val kafkaSSLKeystoreLocation = "./reader/conf/localhost_keystore.jks"
    val kafkaSSLKeystorePassword = "changeit"
    val kafkaSSLTruststoreLocation = "./reader/conf/localhost_truststore.jks"
    val kafkaSSLTruststorePassword = "password"
    val kafkaSSLKeyPassword = "changeit"

    val kafkaReadFrequency = "1" // read every X milliseconds
    val kafkaAutoOffsetReset = "earliest"
    val kafkaPollPeriod = "1"
    val kafkaMaxPollRecords = "1000"
    val kafkaSessionTimeoutMs = "20000"

    // Data localization defaults
    val localizationEnabled = "false"
    val localizationMaxWaittime = "30"
  }

  lazy val rallyConfig: RallyConfig = RallyConfig(Defaults.configFile)

  lazy val secretConfig: SecretConfig = BaseSecretConfig.fromTemplateFilename(rallyConfig, Defaults.secretTemplatePath)

  val serviceName: String = "DatashipReader"
}
