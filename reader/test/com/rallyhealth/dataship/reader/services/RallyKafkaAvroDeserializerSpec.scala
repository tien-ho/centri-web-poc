package com.rallyhealth.dataship.reader.services

import java.security.{KeyPair, KeyPairGenerator, PublicKey, SecureRandom}
import javax.crypto.{Cipher, KeyGenerator}
import javax.crypto.spec.{GCMParameterSpec, SecretKeySpec}

import com.optum.exts.common.model.EncryptedMessage
import com.rallyhealth.dataship.reader.configs.DatashipReaderConfig
import com.rallyhealth.dataship.reader.configs.DatashipReaderConfig.Defaults
import com.rallyhealth.illuminati.v9.BaseSecretConfig
import com.rallyhealth.spartan.v2.config.RallyConfig
import com.rallyhealth.spartan.v2.util.logging.DefaultLogger
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.lang.SerializationUtils
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito.when
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FunSpec, Matchers}
import org.scalatest.mockito.MockitoSugar

import scala.collection.JavaConversions._

class RallyKafkaAvroDeserializerSpec extends FunSpec
  with MockitoSugar
  with Matchers
  with BeforeAndAfter
  with BeforeAndAfterAll
  with DefaultLogger {

  class Fixture {
    val configFile = "test/resources/dataship_test.conf"
    val rallyConfig = RallyConfig(configFile)
    val secretConfig: BaseSecretConfig = BaseSecretConfig.fromTemplateFilename(rallyConfig, Defaults.secretTemplatePath)
    val datashipReaderConfig = new DatashipReaderConfig(rallyConfig, secretConfig)
    val kafkaAvroDeserializer = mock[KafkaAvroDeserializer]
    val decryptor = mock[EncryptedMessageDecrypterService]
    val bytes = "123456".getBytes

    val pairGenerator = KeyPairGenerator.getInstance("RSA")
    pairGenerator.initialize(2048)
    val pair: KeyPair = pairGenerator.generateKeyPair()

    when(decryptor.decrypt(any[EncryptedMessage]())).thenAnswer(new Answer[Array[Byte]]() {
      override def answer(invocation: InvocationOnMock): Array[Byte] = bytes
    })

  }

  def getMockEnvelopeAnswer(pair: KeyPair) = new Answer[EncryptedMessage]() {
    override def answer(invocation: InvocationOnMock): EncryptedMessage = {
      getEnvelope(pair.getPublic, "123456")
    }
  }

  def getMockAnswer = new Answer[String]() {
    override def answer(invocation: InvocationOnMock): String = {
      "123456"
    }
  }

  def getEnvelope(key: PublicKey, s: String): EncryptedMessage = {

    val cekGenerator = KeyGenerator.getInstance("AES")
    cekGenerator.init(256)
    val cek = new SecretKeySpec(cekGenerator.generateKey().getEncoded, "AES")
    val cekFingerprint = DigestUtils.sha256Hex(cek.getEncoded)

    val keyCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding")
    keyCipher.init(1, key)
    val encryptedCEK = keyCipher.doFinal(cek.getEncoded)

    val secureRandom = SecureRandom.getInstance("SHA1PRNG")
    var civ = new Array[Byte](96)
    secureRandom.nextBytes(civ)

    val cipher = Cipher.getInstance("AES/GCM/PKCS5Padding")
    cipher.init(1, cek, new GCMParameterSpec(128, civ), secureRandom)
    val contentBytes = cipher.doFinal(s.getBytes)

    new EncryptedMessage(cekFingerprint, encryptedCEK, civ, contentBytes)

  }

  describe("RallyKafkaAvroDeserializer") {
    it("deserializer should return 1 record") {
      val f = new Fixture
      import f._
      val deserializer = new RallyKafkaAvroDeserializer[String](
        false,
        kafkaAvroDeserializer, decryptor)

      deserializer.configure(null, false)
      val value: String = "123456"
      when(kafkaAvroDeserializer.deserialize(any[String](), any())).thenAnswer(getMockAnswer)
      val record: RallyEligibilityWrapper[String] = deserializer.deserialize("", value.getBytes)
      deserializer.close
      record.value should ===(value)
    }

    it("deserializer should return 1 record when decrypting") {
      val f = new Fixture
      import f._
      val deserializer = new RallyKafkaAvroDeserializer[String](
        true,
        kafkaAvroDeserializer, decryptor)

      deserializer.configure(null, false)
      val envelope = getEnvelope(pair.getPublic, "123456")

      val value = SerializationUtils.serialize(envelope)
      when(kafkaAvroDeserializer.deserialize(any[String](), ArgumentMatchers.eq(value))).thenAnswer(getMockEnvelopeAnswer(pair))
      when(kafkaAvroDeserializer.deserialize(any[String](), ArgumentMatchers.eq("123456".getBytes))).thenAnswer(getMockAnswer)
      val record: RallyEligibilityWrapper[String] = deserializer.deserialize("", value)
      deserializer.close
      record.value should ===("123456")
    }
  }

}
