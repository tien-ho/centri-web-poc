/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package com.optum.exts.common.model

import scala.annotation.switch

/**
 * An encrypted Avro object's encrypted value
 * @param cekFingerprint sha256 digest of the cek
 * @param encryptedCek Rsa encrypted content encryption key
 * @param civ Content initialization vector
 * @param encryptedContent The encrypted Avro value
 */
case class EncryptedMessage(var cekFingerprint: String, var encryptedCek: Array[Byte], var civ: Array[Byte], var encryptedContent: Array[Byte]) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", null, null, null)
  def get(field$: Int): AnyRef = {
    (field$: @switch) match {
      case 0 => {
        cekFingerprint
      }.asInstanceOf[AnyRef]
      case 1 => {
        java.nio.ByteBuffer.wrap(encryptedCek)
      }.asInstanceOf[AnyRef]
      case 2 => {
        java.nio.ByteBuffer.wrap(civ)
      }.asInstanceOf[AnyRef]
      case 3 => {
        java.nio.ByteBuffer.wrap(encryptedContent)
      }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  }
  def put(field$: Int, value: Any): Unit = {
    (field$: @switch) match {
      case 0 => this.cekFingerprint = {
        value.toString
      }.asInstanceOf[String]
      case 1 => this.encryptedCek = {
        value match {
          case (buffer: java.nio.ByteBuffer) => {
            buffer.array()
          }
        }
      }.asInstanceOf[Array[Byte]]
      case 2 => this.civ = {
        value match {
          case (buffer: java.nio.ByteBuffer) => {
            buffer.array()
          }
        }
      }.asInstanceOf[Array[Byte]]
      case 3 => this.encryptedContent = {
        value match {
          case (buffer: java.nio.ByteBuffer) => {
            buffer.array()
          }
        }
      }.asInstanceOf[Array[Byte]]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = EncryptedMessage.SCHEMA$
}

object EncryptedMessage {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"EncryptedMessage\",\"namespace\":\"com.optum.exts.common.model\",\"doc\":\"An encrypted Avro object\'s encrypted value\",\"fields\":[{\"name\":\"cekFingerprint\",\"type\":\"string\",\"doc\":\"sha256 digest of the cek\"},{\"name\":\"encryptedCek\",\"type\":\"bytes\",\"doc\":\"Rsa encrypted content encryption key\"},{\"name\":\"civ\",\"type\":\"bytes\",\"doc\":\"Content initialization vector\"},{\"name\":\"encryptedContent\",\"type\":\"bytes\",\"doc\":\"The encrypted Avro value\"}]}")
}