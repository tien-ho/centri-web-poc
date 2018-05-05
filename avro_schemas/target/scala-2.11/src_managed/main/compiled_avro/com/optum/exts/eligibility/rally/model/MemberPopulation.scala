/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package com.optum.exts.eligibility.rally.model

import scala.annotation.switch

/**
 * @param coverageType from CDB mp.covTypCd
 * @param populationCancelDate from CDB mp.mbrPopIdCancDt
 * @param populationDateAssigned from CDB mp.mbrPopulationAsgnDt
 * @param populationEffectiveDate from CDB mp.mbrPopIdEffDt
 * @param populationId from CDB mp.mbrPopulationId
 * @param populationRuleFired from CDB mp.ruleFired
 * @param populationTimeStamp from CDB mp.rowTmstmp
 */
case class MemberPopulation(var coverageType: String, var populationCancelDate: String, var populationDateAssigned: String, var populationEffectiveDate: String, var populationId: String, var populationRuleFired: String, var populationTimeStamp: String) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", "", "", "", "", "", "")
  def get(field$: Int): AnyRef = {
    (field$: @switch) match {
      case 0 => {
        coverageType
      }.asInstanceOf[AnyRef]
      case 1 => {
        populationCancelDate
      }.asInstanceOf[AnyRef]
      case 2 => {
        populationDateAssigned
      }.asInstanceOf[AnyRef]
      case 3 => {
        populationEffectiveDate
      }.asInstanceOf[AnyRef]
      case 4 => {
        populationId
      }.asInstanceOf[AnyRef]
      case 5 => {
        populationRuleFired
      }.asInstanceOf[AnyRef]
      case 6 => {
        populationTimeStamp
      }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  }
  def put(field$: Int, value: Any): Unit = {
    (field$: @switch) match {
      case 0 => this.coverageType = {
        value.toString
      }.asInstanceOf[String]
      case 1 => this.populationCancelDate = {
        value.toString
      }.asInstanceOf[String]
      case 2 => this.populationDateAssigned = {
        value.toString
      }.asInstanceOf[String]
      case 3 => this.populationEffectiveDate = {
        value.toString
      }.asInstanceOf[String]
      case 4 => this.populationId = {
        value.toString
      }.asInstanceOf[String]
      case 5 => this.populationRuleFired = {
        value.toString
      }.asInstanceOf[String]
      case 6 => this.populationTimeStamp = {
        value.toString
      }.asInstanceOf[String]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = MemberPopulation.SCHEMA$
}

object MemberPopulation {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"MemberPopulation\",\"namespace\":\"com.optum.exts.eligibility.rally.model\",\"fields\":[{\"name\":\"coverageType\",\"type\":\"string\",\"doc\":\"from CDB mp.covTypCd\"},{\"name\":\"populationCancelDate\",\"type\":\"string\",\"doc\":\"from CDB mp.mbrPopIdCancDt\"},{\"name\":\"populationDateAssigned\",\"type\":\"string\",\"doc\":\"from CDB mp.mbrPopulationAsgnDt\"},{\"name\":\"populationEffectiveDate\",\"type\":\"string\",\"doc\":\"from CDB mp.mbrPopIdEffDt\"},{\"name\":\"populationId\",\"type\":\"string\",\"doc\":\"from CDB mp.mbrPopulationId\"},{\"name\":\"populationRuleFired\",\"type\":\"string\",\"doc\":\"from CDB mp.ruleFired\"},{\"name\":\"populationTimeStamp\",\"type\":\"string\",\"doc\":\"from CDB mp.rowTmstmp\"}]}")
}