package com.rallyhealth.dataship.reader.services

import java.security._
import java.util.Base64
import javax.crypto.spec.{GCMParameterSpec, SecretKeySpec}
import javax.crypto.{Cipher, KeyGenerator}

import com.optum.exts.common.model.EncryptedMessage
import com.rallyhealth.dataship.reader.configs.DatashipReaderConfig
import com.rallyhealth.dataship.reader.configs.DatashipReaderConfig.Defaults
import com.rallyhealth.dataship.reader.crypto.{KeyDecryptionKey, KeyDecryptor}
import com.rallyhealth.illuminati.v9.BaseSecretConfig
import com.rallyhealth.spartan.v2.config.{MemoryRallyConfig, RallyConfig}
import com.rallyhealth.spartan.v2.util.logging.DefaultLogger
import org.apache.commons.codec.digest.DigestUtils
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FunSpec, Matchers}
import org.scalatest.mockito.MockitoSugar
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito.when
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer

class EncryptedMessageDecrypterServiceSpec extends FunSpec
  with MockitoSugar
  with Matchers
  with BeforeAndAfter
  with BeforeAndAfterAll
  with DefaultLogger {

  class Fixture {
    val configFile = "test/resources/dataship_test.conf"
    val rallyConfig: RallyConfig = new MemoryRallyConfig(
      Map(
        s"kafka.key.decryption.location" -> "conf/localhost_pkcs8.pem",
        s"kafka.ssl.keystore.location" -> "conf/localhost_keystore.jks",
        s"kafka.ssl.truststore.location" -> "conf/localhost_truststore.jks"
      )
    )
    val secretConfig: BaseSecretConfig = BaseSecretConfig.fromTemplateFilename(rallyConfig, Defaults.secretTemplatePath)
    val datashipReaderConfig = new DatashipReaderConfig(rallyConfig, secretConfig)
  }

  describe("ExtsRecordDecryptor") {
    it("decryptor should decrypt") {
      val f = new Fixture
      import f._

      val pairGenerator = KeyPairGenerator.getInstance("RSA")
      pairGenerator.initialize(2048)
      val pair: KeyPair = pairGenerator.generateKeyPair()
      Base64.getEncoder.encode(pair.getPrivate.getEncoded)

      // encrypt something
      val cekGenerator = KeyGenerator.getInstance("AES")
      cekGenerator.init(256)
      val cek2 = new SecretKeySpec(cekGenerator.generateKey().getEncoded, "AES")
      val mockKeyDecryptor = mock[KeyDecryptor]
      val keyDecryptor = new KeyDecryptor(new KeyDecryptionKey(pair.getPrivate))
      val envelope = getEnvelope(pair.getPublic, cek2, "1234")
      keyDecryptor.decrypt(envelope.encryptedCek)

      val decryptor = new EncryptedMessageDecrypterServiceImpl(datashipReaderConfig, Some(mockKeyDecryptor))

      when(mockKeyDecryptor.decrypt(any())).thenAnswer(new Answer[Key]() {
        override def answer(invocation: InvocationOnMock): Key = {
          cek2
        }
      })

      val decryptedBytes = decryptor.decrypt(envelope)

      decryptedBytes should ===("1234".getBytes)
    }
  }

  it("should construct with non-default key decryptor") {
    val f = new Fixture
    import f._

    val mockKeyDecryptor = None
    val encryptedMessageDecrypterService = new EncryptedMessageDecrypterServiceImpl(datashipReaderConfig, mockKeyDecryptor)
  }

  def getEnvelope(key: PublicKey, cek: Key, s: String): EncryptedMessage = {
    val cekFingerprint = DigestUtils.sha256Hex(cek.getEncoded)

    val keyCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding")
    keyCipher.init(Cipher.ENCRYPT_MODE, key)
    val encryptedCEK = keyCipher.doFinal(cek.getEncoded)

    val secureRandom = SecureRandom.getInstance("SHA1PRNG")
    var civ = new Array[Byte](96)
    secureRandom.nextBytes(civ)

    val cipher = Cipher.getInstance("AES/GCM/PKCS5Padding")
    cipher.init(Cipher.ENCRYPT_MODE, cek, new GCMParameterSpec(128, civ), secureRandom)
    val contentBytes = cipher.doFinal(s.getBytes)

    new EncryptedMessage(cekFingerprint, encryptedCEK, civ, contentBytes)

  }
}
