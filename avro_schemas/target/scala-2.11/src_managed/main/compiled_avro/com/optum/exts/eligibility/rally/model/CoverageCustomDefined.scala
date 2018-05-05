/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package com.optum.exts.eligibility.rally.model

import scala.annotation.switch

/**
 * @param policyNumber from CDB ccdf.lgcyPolNbr
 * @param coverageType from CDB ccdf.covTypCd
 * @param effectiveDate from CDB ccdf.covEffDt
 * @param cancelDate from CDB ccdf.covCancDt
 * @param coverageCustomDefinedTypeCode from CDB ccdf.covCustDefnFldCd
 * @param coverageCustomDefinedText from CDB ccdf.covCustDefnFldTxt
 */
case class CoverageCustomDefined(var policyNumber: String, var coverageType: String, var effectiveDate: String, var cancelDate: String, var coverageCustomDefinedTypeCode: String, var coverageCustomDefinedText: String) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", "", "", "", "", "")
  def get(field$: Int): AnyRef = {
    (field$: @switch) match {
      case 0 => {
        policyNumber
      }.asInstanceOf[AnyRef]
      case 1 => {
        coverageType
      }.asInstanceOf[AnyRef]
      case 2 => {
        effectiveDate
      }.asInstanceOf[AnyRef]
      case 3 => {
        cancelDate
      }.asInstanceOf[AnyRef]
      case 4 => {
        coverageCustomDefinedTypeCode
      }.asInstanceOf[AnyRef]
      case 5 => {
        coverageCustomDefinedText
      }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  }
  def put(field$: Int, value: Any): Unit = {
    (field$: @switch) match {
      case 0 => this.policyNumber = {
        value.toString
      }.asInstanceOf[String]
      case 1 => this.coverageType = {
        value.toString
      }.asInstanceOf[String]
      case 2 => this.effectiveDate = {
        value.toString
      }.asInstanceOf[String]
      case 3 => this.cancelDate = {
        value.toString
      }.asInstanceOf[String]
      case 4 => this.coverageCustomDefinedTypeCode = {
        value.toString
      }.asInstanceOf[String]
      case 5 => this.coverageCustomDefinedText = {
        value.toString
      }.asInstanceOf[String]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = CoverageCustomDefined.SCHEMA$
}

object CoverageCustomDefined {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"CoverageCustomDefined\",\"namespace\":\"com.optum.exts.eligibility.rally.model\",\"fields\":[{\"name\":\"policyNumber\",\"type\":\"string\",\"doc\":\"from CDB ccdf.lgcyPolNbr\"},{\"name\":\"coverageType\",\"type\":\"string\",\"doc\":\"from CDB ccdf.covTypCd\"},{\"name\":\"effectiveDate\",\"type\":\"string\",\"doc\":\"from CDB ccdf.covEffDt\"},{\"name\":\"cancelDate\",\"type\":\"string\",\"doc\":\"from CDB ccdf.covCancDt\"},{\"name\":\"coverageCustomDefinedTypeCode\",\"type\":\"string\",\"doc\":\"from CDB ccdf.covCustDefnFldCd\"},{\"name\":\"coverageCustomDefinedText\",\"type\":\"string\",\"doc\":\"from CDB ccdf.covCustDefnFldTxt\"}]}")
}