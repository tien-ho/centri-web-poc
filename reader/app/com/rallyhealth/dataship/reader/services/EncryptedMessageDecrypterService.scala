package com.rallyhealth.dataship.reader.services

import java.nio.file.{Files, Paths}
import java.security.{Key, KeyFactory, PrivateKey, SecureRandom}
import java.security.spec.PKCS8EncodedKeySpec
import java.util.Base64
import java.util.concurrent.Callable
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec

import com.google.common.cache.{Cache, CacheBuilder}
import com.optum.exts.common.model.EncryptedMessage
import com.rallyhealth.dataship.reader.configs.DatashipReaderConfig
import com.rallyhealth.dataship.reader.crypto.{KeyDecryptionKey, KeyDecryptor}
import com.rallyhealth.spartan.v2.util.logging.DefaultLogger
import org.apache.commons.codec.digest.DigestUtils

/**
  * [[EncryptedMessageDecrypterService]] will take the EncryptedMessage from the kafka topic (with encrypted messages)
  * and decrypt them into bytes for the deserializer to deserialize again via the schema registry.
  *
  * See [https://docs.google.com/document/d/1Z4hOkpmaF00wDzN3_rE-UA5eiGTum80az7o_UzhXQI0/ Dataship RFC] for more details
  */
trait EncryptedMessageDecrypterService {
  def decrypt(envelope: EncryptedMessage): Array[Byte]
}

/**
  * [[EncryptedMessage]] has 4 parts - fingerprint, encryptedCEK, civ (Content Initialization Vector) and encryptedContent
  * The fingerprint and encryptedCEK will be used with the overall decryption key to get the actual contentEncryptionKey.
  * This decrypted CEK will then be used with the civ to decrypt the encryptedContent.
  *
  * See [https://docs.google.com/document/d/1Z4hOkpmaF00wDzN3_rE-UA5eiGTum80az7o_UzhXQI0/ Dataship RFC]
  *
  * @param datashipReaderConfig Configuration for the crypto stuff
  */
class EncryptedMessageDecrypterServiceImpl(
  datashipReaderConfig: DatashipReaderConfig,
  maybeKeyDecryptor: Option[KeyDecryptor] = None)
  extends EncryptedMessageDecrypterService
  with DefaultLogger {
  val KDK_KEY_FACTORY = KeyFactory.getInstance("RSA")
  val keyDecryptionKey = parseKeyDecryptionKey(datashipReaderConfig.kafkaKeyDecryptionKeyLocation)
  private val keyDecryptor: KeyDecryptor = maybeKeyDecryptor.getOrElse(new KeyDecryptor(keyDecryptionKey))
  private val cekFingerprintCache = getCEKFingerprintCache

  val secureRandom: SecureRandom = SecureRandom.getInstance("SHA1PRNG")

  override def decrypt(envelope: EncryptedMessage): Array[Byte] = {
    // first decrypt the encryptedCEK and get the actual Content Encryption Key
    // need to pass in cekFingerprint, and encryptedCEK to get decryptedCEK
    val contentEncryptionKey: Key = cekFingerprintCache.get(envelope.cekFingerprint, new Callable[Key]() {
      override def call(): Key = {
        keyDecryptor.decrypt(envelope.encryptedCek)
      }
    })
    // got the key used to encrypt the content, now decrypt it
    decryptContent(envelope.encryptedContent, envelope.civ, contentEncryptionKey)
  }

  def decryptContent(encryptedContentBytes: Array[Byte], civ: Array[Byte], key: Key): Array[Byte] = {
    // need a new cipher each time because the civ can change
    val cipher: Cipher = Cipher.getInstance("AES/GCM/PKCS5Padding")
    cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, civ), this.secureRandom)
    cipher.doFinal(encryptedContentBytes)

  }
  def getCEKFingerprintCache: Cache[String, Key] = {
    CacheBuilder.newBuilder()
      .maximumSize(datashipReaderConfig.kafkaCEKCacheMaxSize)
      .build[String, Key]()
  }

  def parseKeyDecryptionKey(location: String): KeyDecryptionKey = {
    val bytes = Files.readAllBytes(Paths.get(location))
    new KeyDecryptionKey(parsePrivateKey(bytes))
  }

  def parsePrivateKey(keyBytes: Array[Byte]): PrivateKey = {
    val privateKeyContent = new String(keyBytes)
      .replaceAll("\\n", "")
      .replaceAll("\\r", "")
      .replace("-----BEGIN PRIVATE KEY-----", "")
      .replace("-----END PRIVATE KEY-----", "")
    val encodedKey = Base64.getDecoder.decode(privateKeyContent)
    val spec = new PKCS8EncodedKeySpec(encodedKey)
    KDK_KEY_FACTORY.generatePrivate(spec)
  }
}
