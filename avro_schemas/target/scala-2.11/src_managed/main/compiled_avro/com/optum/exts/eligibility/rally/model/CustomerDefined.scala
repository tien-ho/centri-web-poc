/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package com.optum.exts.eligibility.rally.model

import scala.annotation.switch

/**
 * @param policyNumber from CDB cdf.lgcyPolNbr
 * @param customerDefinedTypeCode from CDB cdf.custDefnFldTypCd
 * @param customerDefinedText from CDB cdf.custDefnFldTxt
 */
case class CustomerDefined(var policyNumber: String, var customerDefinedTypeCode: String, var customerDefinedText: String) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", "", "")
  def get(field$: Int): AnyRef = {
    (field$: @switch) match {
      case 0 => {
        policyNumber
      }.asInstanceOf[AnyRef]
      case 1 => {
        customerDefinedTypeCode
      }.asInstanceOf[AnyRef]
      case 2 => {
        customerDefinedText
      }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  }
  def put(field$: Int, value: Any): Unit = {
    (field$: @switch) match {
      case 0 => this.policyNumber = {
        value.toString
      }.asInstanceOf[String]
      case 1 => this.customerDefinedTypeCode = {
        value.toString
      }.asInstanceOf[String]
      case 2 => this.customerDefinedText = {
        value.toString
      }.asInstanceOf[String]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = CustomerDefined.SCHEMA$
}

object CustomerDefined {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"CustomerDefined\",\"namespace\":\"com.optum.exts.eligibility.rally.model\",\"fields\":[{\"name\":\"policyNumber\",\"type\":\"string\",\"doc\":\"from CDB cdf.lgcyPolNbr\"},{\"name\":\"customerDefinedTypeCode\",\"type\":\"string\",\"doc\":\"from CDB cdf.custDefnFldTypCd\"},{\"name\":\"customerDefinedText\",\"type\":\"string\",\"doc\":\"from CDB cdf.custDefnFldTxt\"}]}")
}