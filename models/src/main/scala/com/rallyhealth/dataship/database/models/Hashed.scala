package com.rallyhealth.dataship.database.models

import com.rallyhealth.dataship.database.services.DatashipDatabaseHmacService
import slick.lifted.MappedTo

/**
  * Type-safe class to represent hashed string values. Extended [[MappedTo]] to support
  * this custom type as [[String]] in Lifted Embedding. This would be used in
  * model classes that need hashed string to make queryable PIIs (e.g. first name).
  */
case class Hashed(value: String) extends AnyVal with MappedTo[String]

object Hashed {
  /**
    * A helper method to create [[Hashed]] object from the given plain string.
    */
  def fromPlainString(value: String)(implicit datashipHmacService: DatashipDatabaseHmacService): Hashed = {
    val hashedValue = datashipHmacService.hmacService.createHMac(value)
    Hashed(hashedValue.asString())
  }

  /**
    * A helper method to create [[Hashed]] object from the given byte array.
    */
  def fromPlainByteArray(value: Array[Byte])(implicit datashipHmacService: DatashipDatabaseHmacService): Hashed = {
    val hashedValue = datashipHmacService.hmacService.createHMac(value)
    Hashed(hashedValue.asString())
  }
}
