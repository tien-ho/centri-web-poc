package com.rallyhealth.dataship.database.services

import com.rallyhealth.enigma.v4.NeoEncryptionService

/**
  * Wrapping [[NeoEncryptionService]] to disambiguate Dataship's [[NeoEncryptionService]] from other
  * [[NeoEncryptionService]]. This way, [[NeoEncryptionService]] can be passed around implicitly and safely in the
  * Dataship application.
  *
  * @param neoEncryptionService Underlying [[NeoEncryptionService]] to encrypt and decrypt payloads.
  */
case class DatashipDatabaseNeoEncryptionService(neoEncryptionService: NeoEncryptionService)
