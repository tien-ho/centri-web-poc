/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package com.optum.exts.eligibility.rally.model

import scala.annotation.switch

/**
 * @param firstName from CDB cs.fstNm / car.repFstNm
 * @param middleName from CDB cs.midlNm
 * @param lastName from CDB cs.lstNm / car.repLstNm
 */
case class PersonName(var firstName: String, var middleName: Option[String] = None, var lastName: String) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", None, "")
  def get(field$: Int): AnyRef = {
    (field$: @switch) match {
      case 0 => {
        firstName
      }.asInstanceOf[AnyRef]
      case 1 => {
        middleName match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 2 => {
        lastName
      }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  }
  def put(field$: Int, value: Any): Unit = {
    (field$: @switch) match {
      case 0 => this.firstName = {
        value.toString
      }.asInstanceOf[String]
      case 1 => this.middleName = {
        value match {
          case null => None
          case _ => Some(value.toString)
        }
      }.asInstanceOf[Option[String]]
      case 2 => this.lastName = {
        value.toString
      }.asInstanceOf[String]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = PersonName.SCHEMA$
}

object PersonName {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"PersonName\",\"namespace\":\"com.optum.exts.eligibility.rally.model\",\"fields\":[{\"name\":\"firstName\",\"type\":\"string\",\"doc\":\"from CDB cs.fstNm / car.repFstNm\"},{\"name\":\"middleName\",\"type\":[\"string\",\"null\"],\"doc\":\"from CDB cs.midlNm\",\"default\":null},{\"name\":\"lastName\",\"type\":\"string\",\"doc\":\"from CDB cs.lstNm / car.repLstNm\"}]}")
}