/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package com.optum.exts.eligibility.rally.model

import scala.annotation.switch

/**
 * @param familyID from CDB cs.famId
 * @param policyNumber from CDB cs.lgcyPolNbr
 * @param personName 
 * @param gender from CDB cs.gdrTyp
 * @param dateOfBirth from CDB cs.bthDt
 * @param cdbRelationshipCode from CDB cs.sbscrRelTypCd
 * @param cdbRelationshipDescription from CDB cs.sbscrRelTypTxt
 * @param dependentSequenceNumber from CDB cs.depnCd
 * @param individualRelationshipCode from CDB cs.topsRelCd
 * @param subscriberEmploymentStartDate from CDB cs.eeStrtDt
 * @param employeeStatus from CDB cs.eeStsTypCd
 * @param permanentAddress 
 * @param primaryPhoneNumber from CDB ct.telNbrPrimary
 * @param alternativePhoneNumber from CDB ct.telNbrAlternate
 * @param altId from CDB cs.lgcyAltMbrId
 * @param subscriberId from CDB cs.lgcySbscrId
 * @param socialSecurityNumber from CDB cs.socSecNbr
 * @param employeeId from CDB cx.unfmtAltCnsmId
 * @param emailAddress from CDB cea.elctrAdrTxt
 * @param cesCustomerNumber from CDB cs.lgcySrcCustId
 * @param cesCustomerName from CDB cs.custNm
 * @param subscriberName 
 * @param subscriberSSN from CDB cs.socSecNbrSub
 * @param maritalStatus from CDB cs.mrtlStsTypCdSub
 * @param authoritativeRepresentative 
 * @param securityLevelCode from CDB cs.secLvlCd
 */
case class Demographics(var familyID: String, var policyNumber: String, var personName: PersonName, var gender: String, var dateOfBirth: String, var cdbRelationshipCode: String, var cdbRelationshipDescription: String, var dependentSequenceNumber: String, var individualRelationshipCode: String, var subscriberEmploymentStartDate: String, var employeeStatus: String, var permanentAddress: Location, var primaryPhoneNumber: Option[String] = None, var alternativePhoneNumber: Option[String] = None, var altId: String, var subscriberId: String, var socialSecurityNumber: String, var employeeId: Option[String] = None, var emailAddress: String, var cesCustomerNumber: String, var cesCustomerName: String, var subscriberName: PersonName, var subscriberSSN: String, var maritalStatus: String, var authoritativeRepresentative: List[AuthoritativeRepresentative], var securityLevelCode: String) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", "", new PersonName, "", "", "", "", "", "", "", "", new Location, None, None, "", "", "", None, "", "", "", new PersonName, "", "", List.empty, "")
  def get(field$: Int): AnyRef = {
    (field$: @switch) match {
      case 0 => {
        familyID
      }.asInstanceOf[AnyRef]
      case 1 => {
        policyNumber
      }.asInstanceOf[AnyRef]
      case 2 => {
        personName
      }.asInstanceOf[AnyRef]
      case 3 => {
        gender
      }.asInstanceOf[AnyRef]
      case 4 => {
        dateOfBirth
      }.asInstanceOf[AnyRef]
      case 5 => {
        cdbRelationshipCode
      }.asInstanceOf[AnyRef]
      case 6 => {
        cdbRelationshipDescription
      }.asInstanceOf[AnyRef]
      case 7 => {
        dependentSequenceNumber
      }.asInstanceOf[AnyRef]
      case 8 => {
        individualRelationshipCode
      }.asInstanceOf[AnyRef]
      case 9 => {
        subscriberEmploymentStartDate
      }.asInstanceOf[AnyRef]
      case 10 => {
        employeeStatus
      }.asInstanceOf[AnyRef]
      case 11 => {
        permanentAddress
      }.asInstanceOf[AnyRef]
      case 12 => {
        primaryPhoneNumber match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 13 => {
        alternativePhoneNumber match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 14 => {
        altId
      }.asInstanceOf[AnyRef]
      case 15 => {
        subscriberId
      }.asInstanceOf[AnyRef]
      case 16 => {
        socialSecurityNumber
      }.asInstanceOf[AnyRef]
      case 17 => {
        employeeId match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 18 => {
        emailAddress
      }.asInstanceOf[AnyRef]
      case 19 => {
        cesCustomerNumber
      }.asInstanceOf[AnyRef]
      case 20 => {
        cesCustomerName
      }.asInstanceOf[AnyRef]
      case 21 => {
        subscriberName
      }.asInstanceOf[AnyRef]
      case 22 => {
        subscriberSSN
      }.asInstanceOf[AnyRef]
      case 23 => {
        maritalStatus
      }.asInstanceOf[AnyRef]
      case 24 => {
        scala.collection.JavaConverters.bufferAsJavaListConverter({
          authoritativeRepresentative map { x =>
            x
          }
        }.toBuffer).asJava
      }.asInstanceOf[AnyRef]
      case 25 => {
        securityLevelCode
      }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  }
  def put(field$: Int, value: Any): Unit = {
    (field$: @switch) match {
      case 0 => this.familyID = {
        value.toString
      }.asInstanceOf[String]
      case 1 => this.policyNumber = {
        value.toString
      }.asInstanceOf[String]
      case 2 => this.personName = {
        value
      }.asInstanceOf[PersonName]
      case 3 => this.gender = {
        value.toString
      }.asInstanceOf[String]
      case 4 => this.dateOfBirth = {
        value.toString
      }.asInstanceOf[String]
      case 5 => this.cdbRelationshipCode = {
        value.toString
      }.asInstanceOf[String]
      case 6 => this.cdbRelationshipDescription = {
        value.toString
      }.asInstanceOf[String]
      case 7 => this.dependentSequenceNumber = {
        value.toString
      }.asInstanceOf[String]
      case 8 => this.individualRelationshipCode = {
        value.toString
      }.asInstanceOf[String]
      case 9 => this.subscriberEmploymentStartDate = {
        value.toString
      }.asInstanceOf[String]
      case 10 => this.employeeStatus = {
        value.toString
      }.asInstanceOf[String]
      case 11 => this.permanentAddress = {
        value
      }.asInstanceOf[Location]
      case 12 => this.primaryPhoneNumber = {
        value match {
          case null => None
          case _ => Some(value.toString)
        }
      }.asInstanceOf[Option[String]]
      case 13 => this.alternativePhoneNumber = {
        value match {
          case null => None
          case _ => Some(value.toString)
        }
      }.asInstanceOf[Option[String]]
      case 14 => this.altId = {
        value.toString
      }.asInstanceOf[String]
      case 15 => this.subscriberId = {
        value.toString
      }.asInstanceOf[String]
      case 16 => this.socialSecurityNumber = {
        value.toString
      }.asInstanceOf[String]
      case 17 => this.employeeId = {
        value match {
          case null => None
          case _ => Some(value.toString)
        }
      }.asInstanceOf[Option[String]]
      case 18 => this.emailAddress = {
        value.toString
      }.asInstanceOf[String]
      case 19 => this.cesCustomerNumber = {
        value.toString
      }.asInstanceOf[String]
      case 20 => this.cesCustomerName = {
        value.toString
      }.asInstanceOf[String]
      case 21 => this.subscriberName = {
        value
      }.asInstanceOf[PersonName]
      case 22 => this.subscriberSSN = {
        value.toString
      }.asInstanceOf[String]
      case 23 => this.maritalStatus = {
        value.toString
      }.asInstanceOf[String]
      case 24 => this.authoritativeRepresentative = {
        value match {
          case (array: java.util.List[_]) => {
            List((scala.collection.JavaConverters.asScalaIteratorConverter(array.iterator).asScala.toSeq map { x =>
              x
            }: _*))
          }
        }
      }.asInstanceOf[List[AuthoritativeRepresentative]]
      case 25 => this.securityLevelCode = {
        value.toString
      }.asInstanceOf[String]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = Demographics.SCHEMA$
}

object Demographics {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Demographics\",\"namespace\":\"com.optum.exts.eligibility.rally.model\",\"fields\":[{\"name\":\"familyID\",\"type\":\"string\",\"doc\":\"from CDB cs.famId\"},{\"name\":\"policyNumber\",\"type\":\"string\",\"doc\":\"from CDB cs.lgcyPolNbr\"},{\"name\":\"personName\",\"type\":{\"type\":\"record\",\"name\":\"PersonName\",\"fields\":[{\"name\":\"firstName\",\"type\":\"string\",\"doc\":\"from CDB cs.fstNm / car.repFstNm\"},{\"name\":\"middleName\",\"type\":[\"string\",\"null\"],\"doc\":\"from CDB cs.midlNm\",\"default\":null},{\"name\":\"lastName\",\"type\":\"string\",\"doc\":\"from CDB cs.lstNm / car.repLstNm\"}]}},{\"name\":\"gender\",\"type\":\"string\",\"doc\":\"from CDB cs.gdrTyp\"},{\"name\":\"dateOfBirth\",\"type\":\"string\",\"doc\":\"from CDB cs.bthDt\"},{\"name\":\"cdbRelationshipCode\",\"type\":\"string\",\"doc\":\"from CDB cs.sbscrRelTypCd\"},{\"name\":\"cdbRelationshipDescription\",\"type\":\"string\",\"doc\":\"from CDB cs.sbscrRelTypTxt\"},{\"name\":\"dependentSequenceNumber\",\"type\":\"string\",\"doc\":\"from CDB cs.depnCd\"},{\"name\":\"individualRelationshipCode\",\"type\":\"string\",\"doc\":\"from CDB cs.topsRelCd\"},{\"name\":\"subscriberEmploymentStartDate\",\"type\":\"string\",\"doc\":\"from CDB cs.eeStrtDt\"},{\"name\":\"employeeStatus\",\"type\":\"string\",\"doc\":\"from CDB cs.eeStsTypCd\"},{\"name\":\"permanentAddress\",\"type\":{\"type\":\"record\",\"name\":\"Location\",\"fields\":[{\"name\":\"postalAddress\",\"type\":{\"type\":\"record\",\"name\":\"PostalAddress\",\"fields\":[{\"name\":\"street1\",\"type\":\"string\",\"doc\":\"from CDB ca.strAdrLn1Txt / car.repStrAdrLn1Txt\"},{\"name\":\"street2\",\"type\":\"string\",\"doc\":\"from CDB ca.strAdrLn2Txt / car.repStrAdrLn2Txt\"},{\"name\":\"city\",\"type\":\"string\",\"doc\":\"from CDB ca.ctyNm / car.repCtyNm\"},{\"name\":\"zip\",\"type\":\"string\",\"doc\":\"from CDB ca.pstCd / car.repPstCd\"},{\"name\":\"zip4\",\"type\":[\"string\",\"null\"],\"doc\":\"from CDB car.repPstExtCd\",\"default\":null},{\"name\":\"stateCode\",\"type\":\"string\",\"doc\":\"from CDB ca.stCd / car.repStCd\"},{\"name\":\"countryCode\",\"type\":\"string\",\"doc\":\"from CDB ca.cntryCd / car.repCntryCd\"},{\"name\":\"countrySubCode\",\"type\":\"string\",\"doc\":\"from CDB car.repCntrySubdivCd\"}]}}]}},{\"name\":\"primaryPhoneNumber\",\"type\":[\"string\",\"null\"],\"doc\":\"from CDB ct.telNbrPrimary\",\"default\":null},{\"name\":\"alternativePhoneNumber\",\"type\":[\"string\",\"null\"],\"doc\":\"from CDB ct.telNbrAlternate\",\"default\":null},{\"name\":\"altId\",\"type\":\"string\",\"doc\":\"from CDB cs.lgcyAltMbrId\"},{\"name\":\"subscriberId\",\"type\":\"string\",\"doc\":\"from CDB cs.lgcySbscrId\"},{\"name\":\"socialSecurityNumber\",\"type\":\"string\",\"doc\":\"from CDB cs.socSecNbr\"},{\"name\":\"employeeId\",\"type\":[\"string\",\"null\"],\"doc\":\"from CDB cx.unfmtAltCnsmId\",\"default\":null},{\"name\":\"emailAddress\",\"type\":\"string\",\"doc\":\"from CDB cea.elctrAdrTxt\"},{\"name\":\"cesCustomerNumber\",\"type\":\"string\",\"doc\":\"from CDB cs.lgcySrcCustId\"},{\"name\":\"cesCustomerName\",\"type\":\"string\",\"doc\":\"from CDB cs.custNm\"},{\"name\":\"subscriberName\",\"type\":\"PersonName\"},{\"name\":\"subscriberSSN\",\"type\":\"string\",\"doc\":\"from CDB cs.socSecNbrSub\"},{\"name\":\"maritalStatus\",\"type\":\"string\",\"doc\":\"from CDB cs.mrtlStsTypCdSub\"},{\"name\":\"authoritativeRepresentative\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"AuthoritativeRepresentative\",\"fields\":[{\"name\":\"personName\",\"type\":\"PersonName\"},{\"name\":\"postalAddress\",\"type\":\"PostalAddress\"}]}}},{\"name\":\"securityLevelCode\",\"type\":\"string\",\"doc\":\"from CDB cs.secLvlCd\"}]}")
}