/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package com.optum.exts.rally.eligibility.model

import scala.annotation.switch

/** @param individualIdentifier The unique key for an Individual from a specific source (e.g. PART_NBR + CNSM_ID + SRC_CD + LGCY_SRC_ID) */
case class RallyEligibilityKey(var individualIdentifier: String) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("")
  def get(field$: Int): AnyRef = {
    (field$: @switch) match {
      case 0 => {
        individualIdentifier
      }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  }
  def put(field$: Int, value: Any): Unit = {
    (field$: @switch) match {
      case 0 => this.individualIdentifier = {
        value.toString
      }.asInstanceOf[String]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = RallyEligibilityKey.SCHEMA$
}

object RallyEligibilityKey {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"RallyEligibilityKey\",\"namespace\":\"com.optum.exts.rally.eligibility.model\",\"fields\":[{\"name\":\"individualIdentifier\",\"type\":\"string\",\"doc\":\"The unique key for an Individual from a specific source (e.g. PART_NBR + CNSM_ID + SRC_CD + LGCY_SRC_ID)\"}]}")
}