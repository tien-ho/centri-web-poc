package com.rallyhealth.dataship.reader.services

import com.optum.exts.eligibility.rally.model._
import org.joda.time.{DateTime, DateTimeZone}

object RallyKafkaConsumerSpecHelper {
  val validAvroRallyEligibility = {
    val postalAddress: PostalAddress = new PostalAddress(
      street1 = "10 Main St",
      street2 = "Empty",
      city = "Conroe",
      zip = "77303",
      stateCode = "TX",
      countryCode = "USA",
      countrySubCode = "USA"
    )

    val personName = new PersonName(firstName = "John", lastName = "Doe")
    val authoritativeRepresentative = new AuthoritativeRepresentative(
      personName, postalAddress
    )

    val demographics = new Demographics(
      familyID = "abc",
      policyNumber = "123",
      personName = personName,
      gender = "M",
      dateOfBirth = "1983-03-31",
      cdbRelationshipCode = "S",
      cdbRelationshipDescription = "Yes",
      dependentSequenceNumber = "1",
      individualRelationshipCode = "S",
      subscriberEmploymentStartDate = "2018-01-01",
      employeeStatus = "E",
      permanentAddress = new Location(postalAddress),
      altId = "123",
      subscriberId = "1234",
      socialSecurityNumber = "123-45-6789",
      emailAddress = "john.doe@john.doe",
      cesCustomerName = "John Doe",
      cesCustomerNumber = "1234",
      subscriberName = personName,
      subscriberSSN = "123-45-6789",
      maritalStatus = "S",
      authoritativeRepresentative = List(authoritativeRepresentative),
      securityLevelCode = "TSS"
    )

    val healthService = new HealthService(policyNumber = "HS1234", "", "", "", "", "")

    val avroRecord = new RallyEligibility(
      individualIdentifier = "cdb:680:1780429788:CS:45B2186",
      partitionNumber = "1",
      consumerId = "123",
      sourceCode = "TEST",
      legacySourceId = "ABC",
      xrefId = "mXbInfEmMjsdjkZ",
      xrefIdPartitionNumber = "mXbInfEmMjsdjkZ",
      personId = List(36L),
      demographics = demographics,
      healthCoverage = List(new HealthCoverage),
      healthServices = List(healthService),
      financialAccounts = List(new FinancialAccount),
      customerDefined = List(new CustomerDefined),
      coverageCustomDefined = List(new CoverageCustomDefined),
      memberPopulation = List(new MemberPopulation),
      sourceLastUpdatedTimestamp = Option(DateTime.now(DateTimeZone.UTC).toString())
    )
    avroRecord
  }
}
