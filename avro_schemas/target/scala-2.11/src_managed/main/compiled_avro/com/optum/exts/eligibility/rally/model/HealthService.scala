/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package com.optum.exts.eligibility.rally.model

import scala.annotation.switch

/**
 * @param policyNumber from CDB hsd.lgcyPolNbr
 * @param healthServiceProductCode from CDB hsd.hltSrvPrdtCd
 * @param healthServiceProductLine from CDB hsd.hltSrvPrdtLnCd
 * @param cancelDate from CDB hsd.hltSrvCancDt
 * @param effectiveDate from CDB hsd.hltSrvEffDt
 * @param eligibilityStatusCode from CDB hsd.eeStsTypCd
 */
case class HealthService(var policyNumber: String, var healthServiceProductCode: String, var healthServiceProductLine: String, var cancelDate: String, var effectiveDate: String, var eligibilityStatusCode: String) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", "", "", "", "", "")
  def get(field$: Int): AnyRef = {
    (field$: @switch) match {
      case 0 => {
        policyNumber
      }.asInstanceOf[AnyRef]
      case 1 => {
        healthServiceProductCode
      }.asInstanceOf[AnyRef]
      case 2 => {
        healthServiceProductLine
      }.asInstanceOf[AnyRef]
      case 3 => {
        cancelDate
      }.asInstanceOf[AnyRef]
      case 4 => {
        effectiveDate
      }.asInstanceOf[AnyRef]
      case 5 => {
        eligibilityStatusCode
      }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  }
  def put(field$: Int, value: Any): Unit = {
    (field$: @switch) match {
      case 0 => this.policyNumber = {
        value.toString
      }.asInstanceOf[String]
      case 1 => this.healthServiceProductCode = {
        value.toString
      }.asInstanceOf[String]
      case 2 => this.healthServiceProductLine = {
        value.toString
      }.asInstanceOf[String]
      case 3 => this.cancelDate = {
        value.toString
      }.asInstanceOf[String]
      case 4 => this.effectiveDate = {
        value.toString
      }.asInstanceOf[String]
      case 5 => this.eligibilityStatusCode = {
        value.toString
      }.asInstanceOf[String]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = HealthService.SCHEMA$
}

object HealthService {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"HealthService\",\"namespace\":\"com.optum.exts.eligibility.rally.model\",\"fields\":[{\"name\":\"policyNumber\",\"type\":\"string\",\"doc\":\"from CDB hsd.lgcyPolNbr\"},{\"name\":\"healthServiceProductCode\",\"type\":\"string\",\"doc\":\"from CDB hsd.hltSrvPrdtCd\"},{\"name\":\"healthServiceProductLine\",\"type\":\"string\",\"doc\":\"from CDB hsd.hltSrvPrdtLnCd\"},{\"name\":\"cancelDate\",\"type\":\"string\",\"doc\":\"from CDB hsd.hltSrvCancDt\"},{\"name\":\"effectiveDate\",\"type\":\"string\",\"doc\":\"from CDB hsd.hltSrvEffDt\"},{\"name\":\"eligibilityStatusCode\",\"type\":\"string\",\"doc\":\"from CDB hsd.eeStsTypCd\"}]}")
}