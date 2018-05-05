package com.rallyhealth.dataship.database.models

object DatashipModelTestFixtures {
  val rawAvroJsonString =
    """{
      |    "rallySourceIndividual": {
      |        "individualIdentifier": "cdb:680:1780429788:CS:45B2186",
      |        "cdbKey": {
      |            "partitionNumber": 680,
      |            "consumerId": 1780429788,
      |            "sourceCode": "CS",
      |            "legacySourceId": "45B2186"
      |        },
      |        "alternateIdentifiers": {
      |            "memberId": "Pxl",
      |            "subscriberId": "OMQcoqlePMrPUaz",
      |            "alternateSubscriberId": "XQKRm",
      |            "familyId": "SGsZyozHWOp",
      |            "cdbXrefId": "mXbInfEmMjsdjkZ",
      |            "cdbXrefIdPartitionNumber": "ilhsV",
      |            "employerAssignedId": "GzEjJogkEWfv"
      |        },
      |        "demographicInfo": {
      |            "firstName": "uTlutxMeihyRedUFGhq",
      |            "lastName": "kZrCQeHgUCZHxozHrVwyrLXN",
      |            "middleName": "lpCmPJu",
      |            "middleInitialText": "l",
      |            "birthDate": "1983-01-23",
      |            "nameSuffixCode": "SF",
      |            "genderTypeCode": "M",
      |            "socialSecurityNumber": "789-35-8205",
      |            "deceasedDate": "1983-12-15",
      |            "maritalStatusTypeCode": "U"
      |        },
      |        "postalAddresses": [
      |            {
      |                "typeCode": "HMXgNQiSmVxXacoajgQsSxJqlk",
      |                "typeDescription": "wcOjpgUNt",
      |                "line1Text": "LcHsJLeEpjhKSLcgZtvPRHYn",
      |                "line2Text": "tDICeUNsrRxhOlrspLrXuhMrdlOrUdX",
      |                "townName": "lOYXlRE",
      |                "postalCode": "77303",
      |                "postalSuffixCode": "5762",
      |                "stateProvinceCode": "VT",
      |                "isoCountryCode": "    ",
      |                "isoCountryDescription": "    ",
      |                "primaryAddressIndicator": true,
      |                "active": true
      |            }
      |        ],
      |        "eligibilityAttributes": {
      |            "eligibilityRelationshipCode": "XaNqdIaSXNLaCfGoochRIG",
      |            "eligibilityRelationshipDescription": "SjkOAAQZIwkhWoyfoYCAmK",
      |            "enrolleeMemberFacingIdentifier": "rNoZbEMyTkQORtk",
      |            "subscriberMemberFacingIdentifier": "PrNfYNHQGwrTZKiVwu",
      |            "memberSequenceNumber": "Q"
      |        },
      |        "employeeStatus": "X",
      |        "hireDate": "2016-12-04",
      |        "personId": 36,
      |        "subscriberSocialSecurityNumber": "932-59-3550",
      |        "active": true,
      |        "sourceLastUpdatedTimestamp": null
      |    },
      |    "rallyMemberships": [
      |        {
      |            "individualIdentifier": "cdb:680:1780429788:CS:45B2186",
      |            "membershipIdentifier": "cdb:coverage:3636268:2017-10-20:VE",
      |            "recordType": "healthService",
      |            "eligibilityAttributes": {
      |                "enrollee": {
      |                    "individualIdentifier": "rfzmcCjirbcwXMwMbnCQvrVnYoQmOx",
      |                    "individualIdentifierSourceSysCode": "axEgxyehfMnbsLHYHEDsVehaHdXLxGz",
      |                    "eid": "wUSpmJrcVoio",
      |                    "eidSourceSysCode": "STMdrZVpFUK",
      |                    "surrogateKey": "gMarFMklRvf",
      |                    "surrogateKeySourceSysCode": "CUUsAdaBPIxSfKHTVkHjCAZGP"
      |                },
      |                "referencePartitionNumberId": "545419545920",
      |                "familyPartnerNumberId": "545419545920",
      |                "researchAuthorizationIndicator": "545419545920"
      |            },
      |            "subscriber": {
      |                "individualIdentifier": "ggyDSjzipSWiKBZZbaRSqRSHBIz",
      |                "individualSourceSysCode": "ox",
      |                "eid": "JKdYNBmSDQBMOfttMxOtQmOHGS",
      |                "eidSourceSysCode": "PjnzmdAZISlTTgTliqNsvWcptP",
      |                "surrogateKey": "uBeEHAxuLeF",
      |                "surrogateKeySourceSysCode": "CbvAiurTBktbPIZUzS"
      |            },
      |            "customerAccount": {
      |                "identifier": "Mt",
      |                "businessArrangementIdentifier": "7354508",
      |                "customerPurchase": [
      |                    {
      |                        "situsStateCode": "hYqirNrX"
      |                    },
      |                    {
      |                        "situsStateCode": "WTNQwbiWR"
      |                    }
      |                ],
      |                "migratedLegacyPolicyNumberId": [
      |                    "Q",
      |                    "cGVSINOVnktBSsclu",
      |                    "ZFJYjRo",
      |                    "WFSDQpPgkriZMBOPqMI",
      |                    "bylyFPHOaYotUCDpFISC",
      |                    "BISVyBZjlhazMLDLGSwKrSM"
      |                ]
      |            },
      |            "active": true,
      |            "sourceLastUpdatedTimestamp": null
      |        },
      |        {
      |            "individualIdentifier": "cdb:680:1780429788:CS:45B2186",
      |            "membershipIdentifier": "cdb:coverage:8289770:2016-09-01:DV",
      |            "recordType": "healthService",
      |            "eligibilityAttributes": {
      |                "enrollee": {
      |                    "individualIdentifier": "cScI",
      |                    "individualIdentifierSourceSysCode": "jJlcjvpytERigokTAtZuvmWMKha",
      |                    "eid": "AWpNvDEcdFNslOmfJAZmRTtijcUK",
      |                    "eidSourceSysCode": "UNvP",
      |                    "surrogateKey": "COufZNKxXyBBEhOojQOGVWzMsabGnW",
      |                    "surrogateKeySourceSysCode": "ZFJXoXXqJQoygdI"
      |                },
      |                "referencePartitionNumberId": "015184229106",
      |                "familyPartnerNumberId": "015184229106",
      |                "researchAuthorizationIndicator": "015184229106"
      |            },
      |            "subscriber": {
      |                "individualIdentifier": "cScI",
      |                "individualSourceSysCode": "jJlcjvpytERigokTAtZuvmWMKha",
      |                "eid": "AWpNvDEcdFNslOmfJAZmRTtijcUK",
      |                "eidSourceSysCode": "UNvP",
      |                "surrogateKey": "COufZNKxXyBBEhOojQOGVWzMsabGnW",
      |                "surrogateKeySourceSysCode": "ZFJXoXXqJQoygdI"
      |            },
      |            "customerAccount": {
      |                "identifier": "001748619557890360675203754",
      |                "businessArrangementIdentifier": "026132516082425676350211260",
      |                "customerPurchase": [
      |                    {
      |                        "situsStateCode": "MN"
      |                    },
      |                    {
      |                        "situsStateCode": "MT"
      |                    }
      |                ],
      |                "migratedLegacyPolicyNumberId": [
      |                    "2721625",
      |                    "1380311"
      |                ]
      |            },
      |            "active": false,
      |            "sourceLastUpdatedTimestamp": null
      |        }
      |    ],
      |    "active": false,
      |    "sourceLastUpdatedTimestamp": "2017-07-02T04:00:00Z"
      |}
    """.stripMargin

  val rawAvroJsonStringMissingSourceLastUpdatedTime =
    """{
      |    "rallySourceIndividual": {
      |        "individualIdentifier": "cdb:680:1780429788:CS:45B2186",
      |        "cdbKey": {
      |            "partitionNumber": 680,
      |            "consumerId": 1780429788,
      |            "sourceCode": "CS",
      |            "legacySourceId": "45B2186"
      |        },
      |        "alternateIdentifiers": {
      |            "memberId": "Pxl",
      |            "subscriberId": "OMQcoqlePMrPUaz",
      |            "alternateSubscriberId": "XQKRm",
      |            "familyId": "SGsZyozHWOp",
      |            "cdbXrefId": "mXbInfEmMjsdjkZ",
      |            "cdbXrefIdPartitionNumber": "ilhsV",
      |            "employerAssignedId": "GzEjJogkEWfv"
      |        },
      |        "demographicInfo": {
      |            "firstName": "uTlutxMeihyRedUFGhq",
      |            "lastName": "kZrCQeHgUCZHxozHrVwyrLXN",
      |            "middleName": "lpCmPJu",
      |            "middleInitialText": "l",
      |            "birthDate": "1983-01-23",
      |            "nameSuffixCode": "SF",
      |            "genderTypeCode": "M",
      |            "socialSecurityNumber": "789-35-8205",
      |            "deceasedDate": "1983-12-15",
      |            "maritalStatusTypeCode": "U"
      |        },
      |        "postalAddresses": [
      |            {
      |                "typeCode": "HMXgNQiSmVxXacoajgQsSxJqlk",
      |                "typeDescription": "wcOjpgUNt",
      |                "line1Text": "LcHsJLeEpjhKSLcgZtvPRHYn",
      |                "line2Text": "tDICeUNsrRxhOlrspLrXuhMrdlOrUdX",
      |                "townName": "lOYXlRE",
      |                "postalCode": null,
      |                "postalSuffixCode": "5762",
      |                "stateProvinceCode": "VT",
      |                "isoCountryCode": "    ",
      |                "isoCountryDescription": "    ",
      |                "primaryAddressIndicator": true,
      |                "active": true
      |            }
      |        ],
      |        "eligibilityAttributes": {
      |            "eligibilityRelationshipCode": "XaNqdIaSXNLaCfGoochRIG",
      |            "eligibilityRelationshipDescription": "SjkOAAQZIwkhWoyfoYCAmK",
      |            "enrolleeMemberFacingIdentifier": "rNoZbEMyTkQORtk",
      |            "subscriberMemberFacingIdentifier": "PrNfYNHQGwrTZKiVwu",
      |            "memberSequenceNumber": "Q"
      |        },
      |        "employeeStatus": "X",
      |        "hireDate": "2016-12-04",
      |        "personId": 36,
      |        "subscriberSocialSecurityNumber": "932-59-3550",
      |        "active": true,
      |        "sourceLastUpdatedTimestamp": null
      |    },
      |    "rallyMemberships": [
      |        {
      |            "individualIdentifier": "cdb:680:1780429788:CS:45B2186",
      |            "membershipIdentifier": "cdb:coverage:3636268:2017-10-20:VE",
      |            "recordType": "healthService",
      |            "eligibilityAttributes": {
      |                "enrollee": {
      |                    "individualIdentifier": "rfzmcCjirbcwXMwMbnCQvrVnYoQmOx",
      |                    "individualIdentifierSourceSysCode": "axEgxyehfMnbsLHYHEDsVehaHdXLxGz",
      |                    "eid": "wUSpmJrcVoio",
      |                    "eidSourceSysCode": "STMdrZVpFUK",
      |                    "surrogateKey": "gMarFMklRvf",
      |                    "surrogateKeySourceSysCode": "CUUsAdaBPIxSfKHTVkHjCAZGP"
      |                },
      |                "referencePartitionNumberId": "545419545920",
      |                "familyPartnerNumberId": "545419545920",
      |                "researchAuthorizationIndicator": "545419545920"
      |            },
      |            "subscriber": {
      |                "individualIdentifier": "ggyDSjzipSWiKBZZbaRSqRSHBIz",
      |                "individualSourceSysCode": "ox",
      |                "eid": "JKdYNBmSDQBMOfttMxOtQmOHGS",
      |                "eidSourceSysCode": "PjnzmdAZISlTTgTliqNsvWcptP",
      |                "surrogateKey": "uBeEHAxuLeF",
      |                "surrogateKeySourceSysCode": "CbvAiurTBktbPIZUzS"
      |            },
      |            "customerAccount": {
      |                "identifier": "Mt",
      |                "businessArrangementIdentifier": "7354508",
      |                "customerPurchase": [
      |                    {
      |                        "situsStateCode": "hYqirNrX"
      |                    },
      |                    {
      |                        "situsStateCode": "WTNQwbiWR"
      |                    }
      |                ],
      |                "migratedLegacyPolicyNumberId": [
      |                    "Q",
      |                    "cGVSINOVnktBSsclu",
      |                    "ZFJYjRo",
      |                    "WFSDQpPgkriZMBOPqMI",
      |                    "bylyFPHOaYotUCDpFISC",
      |                    "BISVyBZjlhazMLDLGSwKrSM"
      |                ]
      |            },
      |            "active": true,
      |            "sourceLastUpdatedTimestamp": null
      |        },
      |        {
      |            "individualIdentifier": "cdb:680:1780429788:CS:45B2186",
      |            "membershipIdentifier": "cdb:coverage:8289770:2016-09-01:DV",
      |            "recordType": "healthService",
      |            "eligibilityAttributes": {
      |                "enrollee": {
      |                    "individualIdentifier": "cScI",
      |                    "individualIdentifierSourceSysCode": "jJlcjvpytERigokTAtZuvmWMKha",
      |                    "eid": "AWpNvDEcdFNslOmfJAZmRTtijcUK",
      |                    "eidSourceSysCode": "UNvP",
      |                    "surrogateKey": "COufZNKxXyBBEhOojQOGVWzMsabGnW",
      |                    "surrogateKeySourceSysCode": "ZFJXoXXqJQoygdI"
      |                },
      |                "referencePartitionNumberId": "015184229106",
      |                "familyPartnerNumberId": "015184229106",
      |                "researchAuthorizationIndicator": "015184229106"
      |            },
      |            "subscriber": {
      |                "individualIdentifier": "cScI",
      |                "individualSourceSysCode": "jJlcjvpytERigokTAtZuvmWMKha",
      |                "eid": "AWpNvDEcdFNslOmfJAZmRTtijcUK",
      |                "eidSourceSysCode": "UNvP",
      |                "surrogateKey": "COufZNKxXyBBEhOojQOGVWzMsabGnW",
      |                "surrogateKeySourceSysCode": "ZFJXoXXqJQoygdI"
      |            },
      |            "customerAccount": {
      |                "identifier": "001748619557890360675203754",
      |                "businessArrangementIdentifier": "026132516082425676350211260",
      |                "customerPurchase": [
      |                    {
      |                        "situsStateCode": "MN"
      |                    },
      |                    {
      |                        "situsStateCode": "MT"
      |                    }
      |                ],
      |                "migratedLegacyPolicyNumberId": [
      |                    "2721625",
      |                    "1380311"
      |                ]
      |            },
      |            "active": false,
      |            "sourceLastUpdatedTimestamp": null
      |        }
      |    ],
      |    "active": false,
      |    "sourceLastUpdatedTimestamp": null
      |}
    """.stripMargin
}
