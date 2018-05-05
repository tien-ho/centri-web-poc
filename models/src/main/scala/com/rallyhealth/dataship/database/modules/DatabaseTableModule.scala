package com.rallyhealth.dataship.database.modules

import com.rallyhealth.dataship.database.models.{IndividualDataTable, IndividualLookupTable}
import slick.lifted.TableQuery

/**
  * Trait that composes all Postgres tables.
  */
trait DatabaseTableModule {
  lazy val individualLookupTable = TableQuery[IndividualLookupTable]
  lazy val individualDataTable = TableQuery[IndividualDataTable]

  /**
    * List of all tables used for test.
    * All tables should be specified to run migrations and respect order of creation.
    */
  // $COVERAGE-OFF$
  lazy val allTables = Seq(
    individualDataTable,
    individualLookupTable
  )
  // $COVERAGE-ON$
}
