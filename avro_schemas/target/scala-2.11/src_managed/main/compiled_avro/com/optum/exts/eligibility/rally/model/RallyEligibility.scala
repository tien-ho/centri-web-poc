/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package com.optum.exts.eligibility.rally.model

import scala.annotation.switch

/**
 * @param individualIdentifier The unique key for an Individual from a specific source (e.g. PART_NBR + CNSM_ID + SRC_CD + LGCY_SRC_ID)
 * @param partitionNumber from CDB cs.partnNbr
 * @param consumerId from CDB cs.cnsmId
 * @param sourceCode from CDB cs.srcCd
 * @param legacySourceId from CDB cs.lgcySrcId
 * @param xrefId from CDB cs.srcCdbXrefId
 * @param xrefIdPartitionNumber from CDB cs.xrefIdPartnNbr
 * @param personId from CIS (more than one is the exception)
 * @param surrogateKey from iMDM
 * @param mdmEid from iMDM
 * @param demographics Demographics
 * @param healthCoverage Health Coverages
 * @param healthServices Health Services
 * @param financialAccounts Financial Accounts
 * @param customerDefined Customer-Defined Fields
 * @param coverageCustomDefined Customer-Defined Coverage Fields
 * @param memberPopulation Member Populations
 * @param sourceLastUpdatedTimestamp 
 */
case class RallyEligibility(var individualIdentifier: String, var partitionNumber: String, var consumerId: String, var sourceCode: String, var legacySourceId: String, var xrefId: String, var xrefIdPartitionNumber: String, var personId: List[Long], var surrogateKey: Option[String] = None, var mdmEid: Option[String] = None, var demographics: Demographics, var healthCoverage: List[HealthCoverage], var healthServices: List[HealthService], var financialAccounts: List[FinancialAccount], var customerDefined: List[CustomerDefined], var coverageCustomDefined: List[CoverageCustomDefined], var memberPopulation: List[MemberPopulation], var sourceLastUpdatedTimestamp: Option[String] = None) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", "", "", "", "", "", "", List.empty, None, None, new Demographics, List.empty, List.empty, List.empty, List.empty, List.empty, List.empty, None)
  def get(field$: Int): AnyRef = {
    (field$: @switch) match {
      case 0 => {
        individualIdentifier
      }.asInstanceOf[AnyRef]
      case 1 => {
        partitionNumber
      }.asInstanceOf[AnyRef]
      case 2 => {
        consumerId
      }.asInstanceOf[AnyRef]
      case 3 => {
        sourceCode
      }.asInstanceOf[AnyRef]
      case 4 => {
        legacySourceId
      }.asInstanceOf[AnyRef]
      case 5 => {
        xrefId
      }.asInstanceOf[AnyRef]
      case 6 => {
        xrefIdPartitionNumber
      }.asInstanceOf[AnyRef]
      case 7 => {
        scala.collection.JavaConverters.bufferAsJavaListConverter({
          personId map { x =>
            x
          }
        }.toBuffer).asJava
      }.asInstanceOf[AnyRef]
      case 8 => {
        surrogateKey match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 9 => {
        mdmEid match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 10 => {
        demographics
      }.asInstanceOf[AnyRef]
      case 11 => {
        scala.collection.JavaConverters.bufferAsJavaListConverter({
          healthCoverage map { x =>
            x
          }
        }.toBuffer).asJava
      }.asInstanceOf[AnyRef]
      case 12 => {
        scala.collection.JavaConverters.bufferAsJavaListConverter({
          healthServices map { x =>
            x
          }
        }.toBuffer).asJava
      }.asInstanceOf[AnyRef]
      case 13 => {
        scala.collection.JavaConverters.bufferAsJavaListConverter({
          financialAccounts map { x =>
            x
          }
        }.toBuffer).asJava
      }.asInstanceOf[AnyRef]
      case 14 => {
        scala.collection.JavaConverters.bufferAsJavaListConverter({
          customerDefined map { x =>
            x
          }
        }.toBuffer).asJava
      }.asInstanceOf[AnyRef]
      case 15 => {
        scala.collection.JavaConverters.bufferAsJavaListConverter({
          coverageCustomDefined map { x =>
            x
          }
        }.toBuffer).asJava
      }.asInstanceOf[AnyRef]
      case 16 => {
        scala.collection.JavaConverters.bufferAsJavaListConverter({
          memberPopulation map { x =>
            x
          }
        }.toBuffer).asJava
      }.asInstanceOf[AnyRef]
      case 17 => {
        sourceLastUpdatedTimestamp match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  }
  def put(field$: Int, value: Any): Unit = {
    (field$: @switch) match {
      case 0 => this.individualIdentifier = {
        value.toString
      }.asInstanceOf[String]
      case 1 => this.partitionNumber = {
        value.toString
      }.asInstanceOf[String]
      case 2 => this.consumerId = {
        value.toString
      }.asInstanceOf[String]
      case 3 => this.sourceCode = {
        value.toString
      }.asInstanceOf[String]
      case 4 => this.legacySourceId = {
        value.toString
      }.asInstanceOf[String]
      case 5 => this.xrefId = {
        value.toString
      }.asInstanceOf[String]
      case 6 => this.xrefIdPartitionNumber = {
        value.toString
      }.asInstanceOf[String]
      case 7 => this.personId = {
        value match {
          case (array: java.util.List[_]) => {
            List((scala.collection.JavaConverters.asScalaIteratorConverter(array.iterator).asScala.toSeq map { x =>
              x
            }: _*))
          }
        }
      }.asInstanceOf[List[Long]]
      case 8 => this.surrogateKey = {
        value match {
          case null => None
          case _ => Some(value.toString)
        }
      }.asInstanceOf[Option[String]]
      case 9 => this.mdmEid = {
        value match {
          case null => None
          case _ => Some(value.toString)
        }
      }.asInstanceOf[Option[String]]
      case 10 => this.demographics = {
        value
      }.asInstanceOf[Demographics]
      case 11 => this.healthCoverage = {
        value match {
          case (array: java.util.List[_]) => {
            List((scala.collection.JavaConverters.asScalaIteratorConverter(array.iterator).asScala.toSeq map { x =>
              x
            }: _*))
          }
        }
      }.asInstanceOf[List[HealthCoverage]]
      case 12 => this.healthServices = {
        value match {
          case (array: java.util.List[_]) => {
            List((scala.collection.JavaConverters.asScalaIteratorConverter(array.iterator).asScala.toSeq map { x =>
              x
            }: _*))
          }
        }
      }.asInstanceOf[List[HealthService]]
      case 13 => this.financialAccounts = {
        value match {
          case (array: java.util.List[_]) => {
            List((scala.collection.JavaConverters.asScalaIteratorConverter(array.iterator).asScala.toSeq map { x =>
              x
            }: _*))
          }
        }
      }.asInstanceOf[List[FinancialAccount]]
      case 14 => this.customerDefined = {
        value match {
          case (array: java.util.List[_]) => {
            List((scala.collection.JavaConverters.asScalaIteratorConverter(array.iterator).asScala.toSeq map { x =>
              x
            }: _*))
          }
        }
      }.asInstanceOf[List[CustomerDefined]]
      case 15 => this.coverageCustomDefined = {
        value match {
          case (array: java.util.List[_]) => {
            List((scala.collection.JavaConverters.asScalaIteratorConverter(array.iterator).asScala.toSeq map { x =>
              x
            }: _*))
          }
        }
      }.asInstanceOf[List[CoverageCustomDefined]]
      case 16 => this.memberPopulation = {
        value match {
          case (array: java.util.List[_]) => {
            List((scala.collection.JavaConverters.asScalaIteratorConverter(array.iterator).asScala.toSeq map { x =>
              x
            }: _*))
          }
        }
      }.asInstanceOf[List[MemberPopulation]]
      case 17 => this.sourceLastUpdatedTimestamp = {
        value match {
          case null => None
          case _ => Some(value.toString)
        }
      }.asInstanceOf[Option[String]]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = RallyEligibility.SCHEMA$
}

object RallyEligibility {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"RallyEligibility\",\"namespace\":\"com.optum.exts.eligibility.rally.model\",\"fields\":[{\"name\":\"individualIdentifier\",\"type\":\"string\",\"doc\":\"The unique key for an Individual from a specific source (e.g. PART_NBR + CNSM_ID + SRC_CD + LGCY_SRC_ID)\"},{\"name\":\"partitionNumber\",\"type\":\"string\",\"doc\":\"from CDB cs.partnNbr\"},{\"name\":\"consumerId\",\"type\":\"string\",\"doc\":\"from CDB cs.cnsmId\"},{\"name\":\"sourceCode\",\"type\":\"string\",\"doc\":\"from CDB cs.srcCd\"},{\"name\":\"legacySourceId\",\"type\":\"string\",\"doc\":\"from CDB cs.lgcySrcId\"},{\"name\":\"xrefId\",\"type\":\"string\",\"doc\":\"from CDB cs.srcCdbXrefId\"},{\"name\":\"xrefIdPartitionNumber\",\"type\":\"string\",\"doc\":\"from CDB cs.xrefIdPartnNbr\"},{\"name\":\"personId\",\"type\":{\"type\":\"array\",\"items\":\"long\"},\"doc\":\"from CIS (more than one is the exception)\"},{\"name\":\"surrogateKey\",\"type\":[\"string\",\"null\"],\"doc\":\"from iMDM\",\"default\":null},{\"name\":\"mdmEid\",\"type\":[\"string\",\"null\"],\"doc\":\"from iMDM\",\"default\":null},{\"name\":\"demographics\",\"type\":{\"type\":\"record\",\"name\":\"Demographics\",\"fields\":[{\"name\":\"familyID\",\"type\":\"string\",\"doc\":\"from CDB cs.famId\"},{\"name\":\"policyNumber\",\"type\":\"string\",\"doc\":\"from CDB cs.lgcyPolNbr\"},{\"name\":\"personName\",\"type\":{\"type\":\"record\",\"name\":\"PersonName\",\"fields\":[{\"name\":\"firstName\",\"type\":\"string\",\"doc\":\"from CDB cs.fstNm / car.repFstNm\"},{\"name\":\"middleName\",\"type\":[\"string\",\"null\"],\"doc\":\"from CDB cs.midlNm\",\"default\":null},{\"name\":\"lastName\",\"type\":\"string\",\"doc\":\"from CDB cs.lstNm / car.repLstNm\"}]}},{\"name\":\"gender\",\"type\":\"string\",\"doc\":\"from CDB cs.gdrTyp\"},{\"name\":\"dateOfBirth\",\"type\":\"string\",\"doc\":\"from CDB cs.bthDt\"},{\"name\":\"cdbRelationshipCode\",\"type\":\"string\",\"doc\":\"from CDB cs.sbscrRelTypCd\"},{\"name\":\"cdbRelationshipDescription\",\"type\":\"string\",\"doc\":\"from CDB cs.sbscrRelTypTxt\"},{\"name\":\"dependentSequenceNumber\",\"type\":\"string\",\"doc\":\"from CDB cs.depnCd\"},{\"name\":\"individualRelationshipCode\",\"type\":\"string\",\"doc\":\"from CDB cs.topsRelCd\"},{\"name\":\"subscriberEmploymentStartDate\",\"type\":\"string\",\"doc\":\"from CDB cs.eeStrtDt\"},{\"name\":\"employeeStatus\",\"type\":\"string\",\"doc\":\"from CDB cs.eeStsTypCd\"},{\"name\":\"permanentAddress\",\"type\":{\"type\":\"record\",\"name\":\"Location\",\"fields\":[{\"name\":\"postalAddress\",\"type\":{\"type\":\"record\",\"name\":\"PostalAddress\",\"fields\":[{\"name\":\"street1\",\"type\":\"string\",\"doc\":\"from CDB ca.strAdrLn1Txt / car.repStrAdrLn1Txt\"},{\"name\":\"street2\",\"type\":\"string\",\"doc\":\"from CDB ca.strAdrLn2Txt / car.repStrAdrLn2Txt\"},{\"name\":\"city\",\"type\":\"string\",\"doc\":\"from CDB ca.ctyNm / car.repCtyNm\"},{\"name\":\"zip\",\"type\":\"string\",\"doc\":\"from CDB ca.pstCd / car.repPstCd\"},{\"name\":\"zip4\",\"type\":[\"string\",\"null\"],\"doc\":\"from CDB car.repPstExtCd\",\"default\":null},{\"name\":\"stateCode\",\"type\":\"string\",\"doc\":\"from CDB ca.stCd / car.repStCd\"},{\"name\":\"countryCode\",\"type\":\"string\",\"doc\":\"from CDB ca.cntryCd / car.repCntryCd\"},{\"name\":\"countrySubCode\",\"type\":\"string\",\"doc\":\"from CDB car.repCntrySubdivCd\"}]}}]}},{\"name\":\"primaryPhoneNumber\",\"type\":[\"string\",\"null\"],\"doc\":\"from CDB ct.telNbrPrimary\",\"default\":null},{\"name\":\"alternativePhoneNumber\",\"type\":[\"string\",\"null\"],\"doc\":\"from CDB ct.telNbrAlternate\",\"default\":null},{\"name\":\"altId\",\"type\":\"string\",\"doc\":\"from CDB cs.lgcyAltMbrId\"},{\"name\":\"subscriberId\",\"type\":\"string\",\"doc\":\"from CDB cs.lgcySbscrId\"},{\"name\":\"socialSecurityNumber\",\"type\":\"string\",\"doc\":\"from CDB cs.socSecNbr\"},{\"name\":\"employeeId\",\"type\":[\"string\",\"null\"],\"doc\":\"from CDB cx.unfmtAltCnsmId\",\"default\":null},{\"name\":\"emailAddress\",\"type\":\"string\",\"doc\":\"from CDB cea.elctrAdrTxt\"},{\"name\":\"cesCustomerNumber\",\"type\":\"string\",\"doc\":\"from CDB cs.lgcySrcCustId\"},{\"name\":\"cesCustomerName\",\"type\":\"string\",\"doc\":\"from CDB cs.custNm\"},{\"name\":\"subscriberName\",\"type\":\"PersonName\"},{\"name\":\"subscriberSSN\",\"type\":\"string\",\"doc\":\"from CDB cs.socSecNbrSub\"},{\"name\":\"maritalStatus\",\"type\":\"string\",\"doc\":\"from CDB cs.mrtlStsTypCdSub\"},{\"name\":\"authoritativeRepresentative\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"AuthoritativeRepresentative\",\"fields\":[{\"name\":\"personName\",\"type\":\"PersonName\"},{\"name\":\"postalAddress\",\"type\":\"PostalAddress\"}]}}},{\"name\":\"securityLevelCode\",\"type\":\"string\",\"doc\":\"from CDB cs.secLvlCd\"}]},\"doc\":\"Demographics\"},{\"name\":\"healthCoverage\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"HealthCoverage\",\"fields\":[{\"name\":\"legalEntity1\",\"type\":\"string\",\"doc\":\"from CDB cpd.cnsmLglEntyNm*\"},{\"name\":\"policyNumber\",\"type\":\"string\",\"doc\":\"from CDB cpd.lgcyPolNbr\"},{\"name\":\"coverageLevelTypeCode\",\"type\":\"string\",\"doc\":\"from CDB cpd.covLvlTypCd\"},{\"name\":\"coverageType\",\"type\":\"string\",\"doc\":\"from CDB cpd.covTypCd\"},{\"name\":\"effectiveDate\",\"type\":\"string\",\"doc\":\"from CDB cpd.covEffDt\"},{\"name\":\"cancelDate\",\"type\":\"string\",\"doc\":\"from CDB cpd.covCancDt\"},{\"name\":\"planVariationCode\",\"type\":\"string\",\"doc\":\"from CDB cpd.lgcyPlnVarCd\"},{\"name\":\"reportCode\",\"type\":\"string\",\"doc\":\"from CDB cpd.lgcyRptCd\"},{\"name\":\"productServiceCode\",\"type\":\"string\",\"doc\":\"from CDB cpd.prdtSrvcTypCd\"},{\"name\":\"eligibilityStatusCode\",\"type\":\"string\",\"doc\":\"from CDB cpd.eeStsTypCd\"},{\"name\":\"marketSite\",\"type\":\"string\",\"doc\":\"from CDB cpd.mktSiteCd\"},{\"name\":\"stateOfIssue\",\"type\":\"string\",\"doc\":\"from CDB cpd.stateOfIssueCd\"},{\"name\":\"legacyBenefitID\",\"type\":\"string\",\"doc\":\"from CDB cpd.lgcyBenPlnId*\"},{\"name\":\"migratedLegacySourceId\",\"type\":[\"string\",\"null\"],\"doc\":\"from CDB msx.mgrtLgcySrcId\",\"default\":null},{\"name\":\"migratedLegacyPolicyNumber\",\"type\":[\"string\",\"null\"],\"doc\":\"from CDB msx.mgrtLgcyPolNbr\",\"default\":null},{\"name\":\"individualGroupTypeCode\",\"type\":\"string\",\"doc\":\"from CDB cpd.indvGrpTypCd\"},{\"name\":\"packagePlanBenefitCode\",\"type\":\"string\",\"doc\":\"from CDB cpd.pbpCd\"},{\"name\":\"hContractId\",\"type\":\"string\",\"doc\":\"from CDB cpd.hCntrctId\"}]}},\"doc\":\"Health Coverages\"},{\"name\":\"healthServices\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"HealthService\",\"fields\":[{\"name\":\"policyNumber\",\"type\":\"string\",\"doc\":\"from CDB hsd.lgcyPolNbr\"},{\"name\":\"healthServiceProductCode\",\"type\":\"string\",\"doc\":\"from CDB hsd.hltSrvPrdtCd\"},{\"name\":\"healthServiceProductLine\",\"type\":\"string\",\"doc\":\"from CDB hsd.hltSrvPrdtLnCd\"},{\"name\":\"cancelDate\",\"type\":\"string\",\"doc\":\"from CDB hsd.hltSrvCancDt\"},{\"name\":\"effectiveDate\",\"type\":\"string\",\"doc\":\"from CDB hsd.hltSrvEffDt\"},{\"name\":\"eligibilityStatusCode\",\"type\":\"string\",\"doc\":\"from CDB hsd.eeStsTypCd\"}]}},\"doc\":\"Health Services\"},{\"name\":\"financialAccounts\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"FinancialAccount\",\"fields\":[{\"name\":\"financialAccountId\",\"type\":\"string\",\"doc\":\"from CDB fa.fincAcctId\"},{\"name\":\"financialAccountTypeCode\",\"type\":\"string\",\"doc\":\"from CDB fa.fincAcctTypCd\"},{\"name\":\"effectiveDate\",\"type\":\"string\",\"doc\":\"from CDB fa.fincAcctEffDt\"},{\"name\":\"cancelDate\",\"type\":\"string\",\"doc\":\"from CDB fa.fincAcctCancDt\"},{\"name\":\"policyNumber\",\"type\":\"string\",\"doc\":\"from CDB fa.fincPolNbr\"},{\"name\":\"policySuffixCode\",\"type\":\"string\",\"doc\":\"from CDB fa.fincPolSufxCd\"},{\"name\":\"financialAccountStatusCode\",\"type\":\"string\",\"doc\":\"from CDB fa.fincAcctStsCd\"},{\"name\":\"coverageLevel\",\"type\":\"string\",\"doc\":\"from CDB fa.covLvlTypCd\"},{\"name\":\"medicalPolicyNumber\",\"type\":\"string\",\"doc\":\"from CDB fa.lgcyPolNbr\"},{\"name\":\"medicalPolicySuffixCode\",\"type\":\"string\",\"doc\":\"from CDB fa.medPolSufxCd\"},{\"name\":\"sharedArrangement\",\"type\":\"string\",\"doc\":\"from CDB fa.shrArngCd\"},{\"name\":\"obligorId\",\"type\":\"string\",\"doc\":\"from CDB fa.shrArngObligCd\"},{\"name\":\"medicalProductTypeCode\",\"type\":\"string\",\"doc\":\"from CDB fa.lgcyPrdtTypCd\"},{\"name\":\"eligibilityStatusCode\",\"type\":\"string\",\"doc\":\"from CDB fa.eeStsTypCd\"},{\"name\":\"iPlanIndicator\",\"type\":\"string\",\"doc\":\"from CDB fa.prdtSrvcTypCd\"}]}},\"doc\":\"Financial Accounts\"},{\"name\":\"customerDefined\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"CustomerDefined\",\"fields\":[{\"name\":\"policyNumber\",\"type\":\"string\",\"doc\":\"from CDB cdf.lgcyPolNbr\"},{\"name\":\"customerDefinedTypeCode\",\"type\":\"string\",\"doc\":\"from CDB cdf.custDefnFldTypCd\"},{\"name\":\"customerDefinedText\",\"type\":\"string\",\"doc\":\"from CDB cdf.custDefnFldTxt\"}]}},\"doc\":\"Customer-Defined Fields\"},{\"name\":\"coverageCustomDefined\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"CoverageCustomDefined\",\"fields\":[{\"name\":\"policyNumber\",\"type\":\"string\",\"doc\":\"from CDB ccdf.lgcyPolNbr\"},{\"name\":\"coverageType\",\"type\":\"string\",\"doc\":\"from CDB ccdf.covTypCd\"},{\"name\":\"effectiveDate\",\"type\":\"string\",\"doc\":\"from CDB ccdf.covEffDt\"},{\"name\":\"cancelDate\",\"type\":\"string\",\"doc\":\"from CDB ccdf.covCancDt\"},{\"name\":\"coverageCustomDefinedTypeCode\",\"type\":\"string\",\"doc\":\"from CDB ccdf.covCustDefnFldCd\"},{\"name\":\"coverageCustomDefinedText\",\"type\":\"string\",\"doc\":\"from CDB ccdf.covCustDefnFldTxt\"}]}},\"doc\":\"Customer-Defined Coverage Fields\"},{\"name\":\"memberPopulation\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"MemberPopulation\",\"fields\":[{\"name\":\"coverageType\",\"type\":\"string\",\"doc\":\"from CDB mp.covTypCd\"},{\"name\":\"populationCancelDate\",\"type\":\"string\",\"doc\":\"from CDB mp.mbrPopIdCancDt\"},{\"name\":\"populationDateAssigned\",\"type\":\"string\",\"doc\":\"from CDB mp.mbrPopulationAsgnDt\"},{\"name\":\"populationEffectiveDate\",\"type\":\"string\",\"doc\":\"from CDB mp.mbrPopIdEffDt\"},{\"name\":\"populationId\",\"type\":\"string\",\"doc\":\"from CDB mp.mbrPopulationId\"},{\"name\":\"populationRuleFired\",\"type\":\"string\",\"doc\":\"from CDB mp.ruleFired\"},{\"name\":\"populationTimeStamp\",\"type\":\"string\",\"doc\":\"from CDB mp.rowTmstmp\"}]}},\"doc\":\"Member Populations\"},{\"name\":\"sourceLastUpdatedTimestamp\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\",\"logicalType\":\"TIMESTAMP\"}],\"default\":null}]}")
}