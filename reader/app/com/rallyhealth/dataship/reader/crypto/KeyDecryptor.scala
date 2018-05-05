package com.rallyhealth.dataship.reader.crypto

import java.security.Key
import javax.crypto.spec.SecretKeySpec
import javax.crypto.Cipher

import org.apache.commons.codec.digest.DigestUtils

/**
  * Decryptor to decrypt the ContentEncryptionKey using the KeyDecryptionKey
  *
  * @param keyDecryptionKey The KDK for the decryptor
  */
class KeyDecryptor(keyDecryptionKey: KeyDecryptionKey) {
  val cipher: Cipher = initializeCipher(keyDecryptionKey)

  def decrypt(bytes: Array[Byte]): Key = {
    val decryptedBytes = this.cipher.doFinal(bytes)
    new SecretKeySpec(decryptedBytes, "AES")
  }

  private def initializeCipher(keyDecryptionKey: KeyDecryptionKey) = {
    val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding")
    cipher.init(Cipher.DECRYPT_MODE, keyDecryptionKey.key)
    cipher
  }

}

/**
  * The overall KDK for the decryption of the messages on the topic
  */
case class KeyDecryptionKey(key: Key) {
  val fingerprint: String = DigestUtils.sha256Hex(key.getEncoded)
}
