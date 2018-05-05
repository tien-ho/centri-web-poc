/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package com.optum.exts.eligibility.rally.model

import scala.annotation.switch

/**
 * @param street1 from CDB ca.strAdrLn1Txt / car.repStrAdrLn1Txt
 * @param street2 from CDB ca.strAdrLn2Txt / car.repStrAdrLn2Txt
 * @param city from CDB ca.ctyNm / car.repCtyNm
 * @param zip from CDB ca.pstCd / car.repPstCd
 * @param zip4 from CDB car.repPstExtCd
 * @param stateCode from CDB ca.stCd / car.repStCd
 * @param countryCode from CDB ca.cntryCd / car.repCntryCd
 * @param countrySubCode from CDB car.repCntrySubdivCd
 */
case class PostalAddress(var street1: String, var street2: String, var city: String, var zip: String, var zip4: Option[String] = None, var stateCode: String, var countryCode: String, var countrySubCode: String) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", "", "", "", None, "", "", "")
  def get(field$: Int): AnyRef = {
    (field$: @switch) match {
      case 0 => {
        street1
      }.asInstanceOf[AnyRef]
      case 1 => {
        street2
      }.asInstanceOf[AnyRef]
      case 2 => {
        city
      }.asInstanceOf[AnyRef]
      case 3 => {
        zip
      }.asInstanceOf[AnyRef]
      case 4 => {
        zip4 match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 5 => {
        stateCode
      }.asInstanceOf[AnyRef]
      case 6 => {
        countryCode
      }.asInstanceOf[AnyRef]
      case 7 => {
        countrySubCode
      }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  }
  def put(field$: Int, value: Any): Unit = {
    (field$: @switch) match {
      case 0 => this.street1 = {
        value.toString
      }.asInstanceOf[String]
      case 1 => this.street2 = {
        value.toString
      }.asInstanceOf[String]
      case 2 => this.city = {
        value.toString
      }.asInstanceOf[String]
      case 3 => this.zip = {
        value.toString
      }.asInstanceOf[String]
      case 4 => this.zip4 = {
        value match {
          case null => None
          case _ => Some(value.toString)
        }
      }.asInstanceOf[Option[String]]
      case 5 => this.stateCode = {
        value.toString
      }.asInstanceOf[String]
      case 6 => this.countryCode = {
        value.toString
      }.asInstanceOf[String]
      case 7 => this.countrySubCode = {
        value.toString
      }.asInstanceOf[String]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = PostalAddress.SCHEMA$
}

object PostalAddress {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"PostalAddress\",\"namespace\":\"com.optum.exts.eligibility.rally.model\",\"fields\":[{\"name\":\"street1\",\"type\":\"string\",\"doc\":\"from CDB ca.strAdrLn1Txt / car.repStrAdrLn1Txt\"},{\"name\":\"street2\",\"type\":\"string\",\"doc\":\"from CDB ca.strAdrLn2Txt / car.repStrAdrLn2Txt\"},{\"name\":\"city\",\"type\":\"string\",\"doc\":\"from CDB ca.ctyNm / car.repCtyNm\"},{\"name\":\"zip\",\"type\":\"string\",\"doc\":\"from CDB ca.pstCd / car.repPstCd\"},{\"name\":\"zip4\",\"type\":[\"string\",\"null\"],\"doc\":\"from CDB car.repPstExtCd\",\"default\":null},{\"name\":\"stateCode\",\"type\":\"string\",\"doc\":\"from CDB ca.stCd / car.repStCd\"},{\"name\":\"countryCode\",\"type\":\"string\",\"doc\":\"from CDB ca.cntryCd / car.repCntryCd\"},{\"name\":\"countrySubCode\",\"type\":\"string\",\"doc\":\"from CDB car.repCntrySubdivCd\"}]}")
}