/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package com.optum.exts.eligibility.rally.model

import scala.annotation.switch

case class AuthoritativeRepresentative(var personName: PersonName, var postalAddress: PostalAddress) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this(new PersonName, new PostalAddress)
  def get(field$: Int): AnyRef = {
    (field$: @switch) match {
      case 0 => {
        personName
      }.asInstanceOf[AnyRef]
      case 1 => {
        postalAddress
      }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  }
  def put(field$: Int, value: Any): Unit = {
    (field$: @switch) match {
      case 0 => this.personName = {
        value
      }.asInstanceOf[PersonName]
      case 1 => this.postalAddress = {
        value
      }.asInstanceOf[PostalAddress]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = AuthoritativeRepresentative.SCHEMA$
}

object AuthoritativeRepresentative {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"AuthoritativeRepresentative\",\"namespace\":\"com.optum.exts.eligibility.rally.model\",\"fields\":[{\"name\":\"personName\",\"type\":{\"type\":\"record\",\"name\":\"PersonName\",\"fields\":[{\"name\":\"firstName\",\"type\":\"string\",\"doc\":\"from CDB cs.fstNm / car.repFstNm\"},{\"name\":\"middleName\",\"type\":[\"string\",\"null\"],\"doc\":\"from CDB cs.midlNm\",\"default\":null},{\"name\":\"lastName\",\"type\":\"string\",\"doc\":\"from CDB cs.lstNm / car.repLstNm\"}]}},{\"name\":\"postalAddress\",\"type\":{\"type\":\"record\",\"name\":\"PostalAddress\",\"fields\":[{\"name\":\"street1\",\"type\":\"string\",\"doc\":\"from CDB ca.strAdrLn1Txt / car.repStrAdrLn1Txt\"},{\"name\":\"street2\",\"type\":\"string\",\"doc\":\"from CDB ca.strAdrLn2Txt / car.repStrAdrLn2Txt\"},{\"name\":\"city\",\"type\":\"string\",\"doc\":\"from CDB ca.ctyNm / car.repCtyNm\"},{\"name\":\"zip\",\"type\":\"string\",\"doc\":\"from CDB ca.pstCd / car.repPstCd\"},{\"name\":\"zip4\",\"type\":[\"string\",\"null\"],\"doc\":\"from CDB car.repPstExtCd\",\"default\":null},{\"name\":\"stateCode\",\"type\":\"string\",\"doc\":\"from CDB ca.stCd / car.repStCd\"},{\"name\":\"countryCode\",\"type\":\"string\",\"doc\":\"from CDB ca.cntryCd / car.repCntryCd\"},{\"name\":\"countrySubCode\",\"type\":\"string\",\"doc\":\"from CDB car.repCntrySubdivCd\"}]}}]}")
}