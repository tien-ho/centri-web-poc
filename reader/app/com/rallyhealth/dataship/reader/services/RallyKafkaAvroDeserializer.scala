package com.rallyhealth.dataship.reader.services

import java.util

import com.optum.exts.common.model.EncryptedMessage
import com.rallyhealth.spartan.v2.util.logging.DefaultLogger
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import org.apache.kafka.common.serialization.Deserializer

/**
  * Custom Kafka deserializer to take a byte array and either decrypt it first if the feature flag is true, else just
  * deserialize it using the schema registry client.
  */
class RallyKafkaAvroDeserializer[T](
  decryptMessages: Boolean,
  kafkaAvroDeserializer: KafkaAvroDeserializer, encryptedEnvelopeDecrypterService: EncryptedMessageDecrypterService)
  extends Deserializer[RallyEligibilityWrapper[T]]
  with DefaultLogger {

  override def deserialize(s: String, bytes: Array[Byte]): RallyEligibilityWrapper[T] = {
    // decrypt byte array first
    if (decryptMessages) {
      // need to deserialize twice. once to deserialize the envelope, then to get the actual contents
      val avroRecord = kafkaAvroDeserializer.deserialize("", bytes).asInstanceOf[EncryptedMessage]
      val decryptedBytes = encryptedEnvelopeDecrypterService.decrypt(avroRecord)
      val eligibility = kafkaAvroDeserializer.deserialize("", decryptedBytes).asInstanceOf[T]
      new RallyEligibilityWrapper[T](eligibility, decryptedBytes)
    } else {
      val eligibility = kafkaAvroDeserializer.deserialize("", bytes).asInstanceOf[T]
      new RallyEligibilityWrapper[T](eligibility, bytes)
    }
  }

  override def close(): Unit = {
    kafkaAvroDeserializer.close()
  }

  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {
    kafkaAvroDeserializer.configure(configs, isKey)
  }
}
