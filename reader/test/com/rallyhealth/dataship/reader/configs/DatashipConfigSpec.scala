package com.rallyhealth.dataship.reader.configs

import java.nio.charset.StandardCharsets

import com.rallyhealth.dataship.database.models.Hashed
import com.rallyhealth.dataship.database.services.DatashipDatabaseHmacService
import com.rallyhealth.dataship.reader.setup.DatashipFakeApplicationFactory
import com.rallyhealth.enigma.v4.Encrypted
import org.scalatest.FunSpec

import scala.concurrent.ExecutionContext

class DatashipConfigSpec extends FunSpec with DatashipFakeApplicationFactory {

  val datashipContext = datashipReaderModuleFromTestContext
  val datashipConfig: DatashipReaderConfig = datashipContext.configModule.datashipConfig

  it("should add custom play crypto secret in play configuration") {
    assert(datashipContext.configuration.getString("play.crypto.secret").isDefined)
    assert(
      datashipContext.configuration.getString("play.crypto.secret").get ==
        "Q>y09kEj6RmU;Rv/8wm[?H4qU<?DI7FehfGr9=F84dH`i:BdhaxK1yM17/avWYsa"
    )
  }

  it("should get all the Dataship reader config") {
    assert(datashipConfig.loggingSecureEnabled === false)
    assert(datashipConfig.loggingSecureLinkEnabled === false)
    assert(
      datashipConfig.loggingEncryptKey ===
        "bf 2c 84 20 df e4 e5 cf 3a 82 07 7d ba 1d fe 59 f5 9e 53 de a1 a6 9c ff 65 3e 0c 60 d0 b0 08 78"
    )
    assert(
      datashipConfig.serviceHashKey.value ===
        "57 EE 46 C6 EB D6 4C EC E9 C4 71 C7 C7 5E 53 1E 62 0F B6 1B 8E 28 47 4E AA 55 90 A7 33 E8 FC 09"
    )
    assert(
      datashipConfig.serviceEncryptDatabaseKey.value ===
        "49 97 ED 33 72 81 A5 AA 3A 04 45 D5 87 47 B1 DE BD CF 1B AB 5D E9 36 7B 90 03 C2 8F 09 AB BC 92"
    )
    assert(datashipConfig.playSecret === "Q>y09kEj6RmU;Rv/8wm[?H4qU<?DI7FehfGr9=F84dH`i:BdhaxK1yM17/avWYsa")
    assert(datashipConfig.dataLocalizationEnabled === false)
    assert(datashipConfig.dataLocalizationMaxWaitTimeSec === 30)
  }

  it("should get all Kafka related Dataship reader config") {
    assert(datashipConfig.kafkaGroupId === "RallyConsumerGroupLocal")
    assert(datashipConfig.kafkaTopic === "DE_STREAMS.ctc.cdc.rally_encrypted.eligibility.v1")
    assert(datashipConfig.kafkaMaxPollRecords === "1000")
    assert(datashipConfig.kafkaSessionTimeoutMs === "20000")
  }

  it("should set execution context") {
    assert(datashipContext.executionContext.isInstanceOf[ExecutionContext])
  }

  describe("HMAC service config") {
    it("should hash a string value") {
      implicit lazy val hmacService = DatashipDatabaseHmacService(datashipConfig.databaseHmacService)

      val hash = Hashed.fromPlainString("tryhashingthis")
      assert(hash.value === "klH06+zVrp4XjdHOlbvrDoaECSMf/Tq3TcSElQgpZzc=")
    }

    it("should hash a byte array value") {
      implicit lazy val hmacService = DatashipDatabaseHmacService(datashipConfig.databaseHmacService)

      val hash = Hashed.fromPlainByteArray("tryhashingthis".getBytes(StandardCharsets.UTF_8))
      assert(hash.value === "klH06+zVrp4XjdHOlbvrDoaECSMf/Tq3TcSElQgpZzc=")
    }
  }
}
