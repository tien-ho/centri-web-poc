/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package com.optum.exts.eligibility.rally.model

import scala.annotation.switch

/**
 * @param financialAccountId from CDB fa.fincAcctId
 * @param financialAccountTypeCode from CDB fa.fincAcctTypCd
 * @param effectiveDate from CDB fa.fincAcctEffDt
 * @param cancelDate from CDB fa.fincAcctCancDt
 * @param policyNumber from CDB fa.fincPolNbr
 * @param policySuffixCode from CDB fa.fincPolSufxCd
 * @param financialAccountStatusCode from CDB fa.fincAcctStsCd
 * @param coverageLevel from CDB fa.covLvlTypCd
 * @param medicalPolicyNumber from CDB fa.lgcyPolNbr
 * @param medicalPolicySuffixCode from CDB fa.medPolSufxCd
 * @param sharedArrangement from CDB fa.shrArngCd
 * @param obligorId from CDB fa.shrArngObligCd
 * @param medicalProductTypeCode from CDB fa.lgcyPrdtTypCd
 * @param eligibilityStatusCode from CDB fa.eeStsTypCd
 * @param iPlanIndicator from CDB fa.prdtSrvcTypCd
 */
case class FinancialAccount(var financialAccountId: String, var financialAccountTypeCode: String, var effectiveDate: String, var cancelDate: String, var policyNumber: String, var policySuffixCode: String, var financialAccountStatusCode: String, var coverageLevel: String, var medicalPolicyNumber: String, var medicalPolicySuffixCode: String, var sharedArrangement: String, var obligorId: String, var medicalProductTypeCode: String, var eligibilityStatusCode: String, var iPlanIndicator: String) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
  def get(field$: Int): AnyRef = {
    (field$: @switch) match {
      case 0 => {
        financialAccountId
      }.asInstanceOf[AnyRef]
      case 1 => {
        financialAccountTypeCode
      }.asInstanceOf[AnyRef]
      case 2 => {
        effectiveDate
      }.asInstanceOf[AnyRef]
      case 3 => {
        cancelDate
      }.asInstanceOf[AnyRef]
      case 4 => {
        policyNumber
      }.asInstanceOf[AnyRef]
      case 5 => {
        policySuffixCode
      }.asInstanceOf[AnyRef]
      case 6 => {
        financialAccountStatusCode
      }.asInstanceOf[AnyRef]
      case 7 => {
        coverageLevel
      }.asInstanceOf[AnyRef]
      case 8 => {
        medicalPolicyNumber
      }.asInstanceOf[AnyRef]
      case 9 => {
        medicalPolicySuffixCode
      }.asInstanceOf[AnyRef]
      case 10 => {
        sharedArrangement
      }.asInstanceOf[AnyRef]
      case 11 => {
        obligorId
      }.asInstanceOf[AnyRef]
      case 12 => {
        medicalProductTypeCode
      }.asInstanceOf[AnyRef]
      case 13 => {
        eligibilityStatusCode
      }.asInstanceOf[AnyRef]
      case 14 => {
        iPlanIndicator
      }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  }
  def put(field$: Int, value: Any): Unit = {
    (field$: @switch) match {
      case 0 => this.financialAccountId = {
        value.toString
      }.asInstanceOf[String]
      case 1 => this.financialAccountTypeCode = {
        value.toString
      }.asInstanceOf[String]
      case 2 => this.effectiveDate = {
        value.toString
      }.asInstanceOf[String]
      case 3 => this.cancelDate = {
        value.toString
      }.asInstanceOf[String]
      case 4 => this.policyNumber = {
        value.toString
      }.asInstanceOf[String]
      case 5 => this.policySuffixCode = {
        value.toString
      }.asInstanceOf[String]
      case 6 => this.financialAccountStatusCode = {
        value.toString
      }.asInstanceOf[String]
      case 7 => this.coverageLevel = {
        value.toString
      }.asInstanceOf[String]
      case 8 => this.medicalPolicyNumber = {
        value.toString
      }.asInstanceOf[String]
      case 9 => this.medicalPolicySuffixCode = {
        value.toString
      }.asInstanceOf[String]
      case 10 => this.sharedArrangement = {
        value.toString
      }.asInstanceOf[String]
      case 11 => this.obligorId = {
        value.toString
      }.asInstanceOf[String]
      case 12 => this.medicalProductTypeCode = {
        value.toString
      }.asInstanceOf[String]
      case 13 => this.eligibilityStatusCode = {
        value.toString
      }.asInstanceOf[String]
      case 14 => this.iPlanIndicator = {
        value.toString
      }.asInstanceOf[String]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = FinancialAccount.SCHEMA$
}

object FinancialAccount {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"FinancialAccount\",\"namespace\":\"com.optum.exts.eligibility.rally.model\",\"fields\":[{\"name\":\"financialAccountId\",\"type\":\"string\",\"doc\":\"from CDB fa.fincAcctId\"},{\"name\":\"financialAccountTypeCode\",\"type\":\"string\",\"doc\":\"from CDB fa.fincAcctTypCd\"},{\"name\":\"effectiveDate\",\"type\":\"string\",\"doc\":\"from CDB fa.fincAcctEffDt\"},{\"name\":\"cancelDate\",\"type\":\"string\",\"doc\":\"from CDB fa.fincAcctCancDt\"},{\"name\":\"policyNumber\",\"type\":\"string\",\"doc\":\"from CDB fa.fincPolNbr\"},{\"name\":\"policySuffixCode\",\"type\":\"string\",\"doc\":\"from CDB fa.fincPolSufxCd\"},{\"name\":\"financialAccountStatusCode\",\"type\":\"string\",\"doc\":\"from CDB fa.fincAcctStsCd\"},{\"name\":\"coverageLevel\",\"type\":\"string\",\"doc\":\"from CDB fa.covLvlTypCd\"},{\"name\":\"medicalPolicyNumber\",\"type\":\"string\",\"doc\":\"from CDB fa.lgcyPolNbr\"},{\"name\":\"medicalPolicySuffixCode\",\"type\":\"string\",\"doc\":\"from CDB fa.medPolSufxCd\"},{\"name\":\"sharedArrangement\",\"type\":\"string\",\"doc\":\"from CDB fa.shrArngCd\"},{\"name\":\"obligorId\",\"type\":\"string\",\"doc\":\"from CDB fa.shrArngObligCd\"},{\"name\":\"medicalProductTypeCode\",\"type\":\"string\",\"doc\":\"from CDB fa.lgcyPrdtTypCd\"},{\"name\":\"eligibilityStatusCode\",\"type\":\"string\",\"doc\":\"from CDB fa.eeStsTypCd\"},{\"name\":\"iPlanIndicator\",\"type\":\"string\",\"doc\":\"from CDB fa.prdtSrvcTypCd\"}]}")
}