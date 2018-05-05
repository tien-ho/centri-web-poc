package com.rallyhealth.dataship.database.helpers

import com.rallyhealth.core.database.postgres.v2.PGSpecHelper
import com.rallyhealth.core.database.postgres.v2.interfaces.{BeforeTestSuite, CreateDBSchema, DBSpecHelper, DatabaseProfile}
import com.rallyhealth.dataship.database.modules.DatabaseTableModule
import org.scalatest.time.{Seconds, Span}
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._

/**
  * Helper trait for testing DAOs.
  *
  * [[PGSpecHelper]] starts docker container for each test case and create all related tables
  * that will be listed at schemas. Caveat is that the schema creation can cause race condition
  * if multiple tests run at the same time. Running these DB specs sequentially is a work around.
  */
trait DatashipDaoSpecHelper extends PGSpecHelper with DatabaseTableModule { self: DatabaseProfile =>
  lazy val schemas: PostgresProfile.SchemaDescription = allTables.map(_.schema).reduce(_ ++ _)

  override lazy val dbName = "dataship"
}
