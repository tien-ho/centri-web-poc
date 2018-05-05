/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package com.optum.exts.eligibility.rally.model

import scala.annotation.switch

/**
 * @param legalEntity1 from CDB cpd.cnsmLglEntyNm*
 * @param policyNumber from CDB cpd.lgcyPolNbr
 * @param coverageLevelTypeCode from CDB cpd.covLvlTypCd
 * @param coverageType from CDB cpd.covTypCd
 * @param effectiveDate from CDB cpd.covEffDt
 * @param cancelDate from CDB cpd.covCancDt
 * @param planVariationCode from CDB cpd.lgcyPlnVarCd
 * @param reportCode from CDB cpd.lgcyRptCd
 * @param productServiceCode from CDB cpd.prdtSrvcTypCd
 * @param eligibilityStatusCode from CDB cpd.eeStsTypCd
 * @param marketSite from CDB cpd.mktSiteCd
 * @param stateOfIssue from CDB cpd.stateOfIssueCd
 * @param legacyBenefitID from CDB cpd.lgcyBenPlnId*
 * @param migratedLegacySourceId from CDB msx.mgrtLgcySrcId
 * @param migratedLegacyPolicyNumber from CDB msx.mgrtLgcyPolNbr
 * @param individualGroupTypeCode from CDB cpd.indvGrpTypCd
 * @param packagePlanBenefitCode from CDB cpd.pbpCd
 * @param hContractId from CDB cpd.hCntrctId
 */
case class HealthCoverage(var legalEntity1: String, var policyNumber: String, var coverageLevelTypeCode: String, var coverageType: String, var effectiveDate: String, var cancelDate: String, var planVariationCode: String, var reportCode: String, var productServiceCode: String, var eligibilityStatusCode: String, var marketSite: String, var stateOfIssue: String, var legacyBenefitID: String, var migratedLegacySourceId: Option[String] = None, var migratedLegacyPolicyNumber: Option[String] = None, var individualGroupTypeCode: String, var packagePlanBenefitCode: String, var hContractId: String) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", "", "", "", "", "", "", "", "", "", "", "", "", None, None, "", "", "")
  def get(field$: Int): AnyRef = {
    (field$: @switch) match {
      case 0 => {
        legalEntity1
      }.asInstanceOf[AnyRef]
      case 1 => {
        policyNumber
      }.asInstanceOf[AnyRef]
      case 2 => {
        coverageLevelTypeCode
      }.asInstanceOf[AnyRef]
      case 3 => {
        coverageType
      }.asInstanceOf[AnyRef]
      case 4 => {
        effectiveDate
      }.asInstanceOf[AnyRef]
      case 5 => {
        cancelDate
      }.asInstanceOf[AnyRef]
      case 6 => {
        planVariationCode
      }.asInstanceOf[AnyRef]
      case 7 => {
        reportCode
      }.asInstanceOf[AnyRef]
      case 8 => {
        productServiceCode
      }.asInstanceOf[AnyRef]
      case 9 => {
        eligibilityStatusCode
      }.asInstanceOf[AnyRef]
      case 10 => {
        marketSite
      }.asInstanceOf[AnyRef]
      case 11 => {
        stateOfIssue
      }.asInstanceOf[AnyRef]
      case 12 => {
        legacyBenefitID
      }.asInstanceOf[AnyRef]
      case 13 => {
        migratedLegacySourceId match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 14 => {
        migratedLegacyPolicyNumber match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 15 => {
        individualGroupTypeCode
      }.asInstanceOf[AnyRef]
      case 16 => {
        packagePlanBenefitCode
      }.asInstanceOf[AnyRef]
      case 17 => {
        hContractId
      }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  }
  def put(field$: Int, value: Any): Unit = {
    (field$: @switch) match {
      case 0 => this.legalEntity1 = {
        value.toString
      }.asInstanceOf[String]
      case 1 => this.policyNumber = {
        value.toString
      }.asInstanceOf[String]
      case 2 => this.coverageLevelTypeCode = {
        value.toString
      }.asInstanceOf[String]
      case 3 => this.coverageType = {
        value.toString
      }.asInstanceOf[String]
      case 4 => this.effectiveDate = {
        value.toString
      }.asInstanceOf[String]
      case 5 => this.cancelDate = {
        value.toString
      }.asInstanceOf[String]
      case 6 => this.planVariationCode = {
        value.toString
      }.asInstanceOf[String]
      case 7 => this.reportCode = {
        value.toString
      }.asInstanceOf[String]
      case 8 => this.productServiceCode = {
        value.toString
      }.asInstanceOf[String]
      case 9 => this.eligibilityStatusCode = {
        value.toString
      }.asInstanceOf[String]
      case 10 => this.marketSite = {
        value.toString
      }.asInstanceOf[String]
      case 11 => this.stateOfIssue = {
        value.toString
      }.asInstanceOf[String]
      case 12 => this.legacyBenefitID = {
        value.toString
      }.asInstanceOf[String]
      case 13 => this.migratedLegacySourceId = {
        value match {
          case null => None
          case _ => Some(value.toString)
        }
      }.asInstanceOf[Option[String]]
      case 14 => this.migratedLegacyPolicyNumber = {
        value match {
          case null => None
          case _ => Some(value.toString)
        }
      }.asInstanceOf[Option[String]]
      case 15 => this.individualGroupTypeCode = {
        value.toString
      }.asInstanceOf[String]
      case 16 => this.packagePlanBenefitCode = {
        value.toString
      }.asInstanceOf[String]
      case 17 => this.hContractId = {
        value.toString
      }.asInstanceOf[String]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = HealthCoverage.SCHEMA$
}

object HealthCoverage {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"HealthCoverage\",\"namespace\":\"com.optum.exts.eligibility.rally.model\",\"fields\":[{\"name\":\"legalEntity1\",\"type\":\"string\",\"doc\":\"from CDB cpd.cnsmLglEntyNm*\"},{\"name\":\"policyNumber\",\"type\":\"string\",\"doc\":\"from CDB cpd.lgcyPolNbr\"},{\"name\":\"coverageLevelTypeCode\",\"type\":\"string\",\"doc\":\"from CDB cpd.covLvlTypCd\"},{\"name\":\"coverageType\",\"type\":\"string\",\"doc\":\"from CDB cpd.covTypCd\"},{\"name\":\"effectiveDate\",\"type\":\"string\",\"doc\":\"from CDB cpd.covEffDt\"},{\"name\":\"cancelDate\",\"type\":\"string\",\"doc\":\"from CDB cpd.covCancDt\"},{\"name\":\"planVariationCode\",\"type\":\"string\",\"doc\":\"from CDB cpd.lgcyPlnVarCd\"},{\"name\":\"reportCode\",\"type\":\"string\",\"doc\":\"from CDB cpd.lgcyRptCd\"},{\"name\":\"productServiceCode\",\"type\":\"string\",\"doc\":\"from CDB cpd.prdtSrvcTypCd\"},{\"name\":\"eligibilityStatusCode\",\"type\":\"string\",\"doc\":\"from CDB cpd.eeStsTypCd\"},{\"name\":\"marketSite\",\"type\":\"string\",\"doc\":\"from CDB cpd.mktSiteCd\"},{\"name\":\"stateOfIssue\",\"type\":\"string\",\"doc\":\"from CDB cpd.stateOfIssueCd\"},{\"name\":\"legacyBenefitID\",\"type\":\"string\",\"doc\":\"from CDB cpd.lgcyBenPlnId*\"},{\"name\":\"migratedLegacySourceId\",\"type\":[\"string\",\"null\"],\"doc\":\"from CDB msx.mgrtLgcySrcId\",\"default\":null},{\"name\":\"migratedLegacyPolicyNumber\",\"type\":[\"string\",\"null\"],\"doc\":\"from CDB msx.mgrtLgcyPolNbr\",\"default\":null},{\"name\":\"individualGroupTypeCode\",\"type\":\"string\",\"doc\":\"from CDB cpd.indvGrpTypCd\"},{\"name\":\"packagePlanBenefitCode\",\"type\":\"string\",\"doc\":\"from CDB cpd.pbpCd\"},{\"name\":\"hContractId\",\"type\":\"string\",\"doc\":\"from CDB cpd.hCntrctId\"}]}")
}