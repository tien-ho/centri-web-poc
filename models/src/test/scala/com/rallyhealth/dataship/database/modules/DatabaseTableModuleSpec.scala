package com.rallyhealth.dataship.database.modules

import com.rallyhealth.dataship.database.models.{IndividualDataTable, IndividualLookupTable}
import org.scalatest.FunSpec
import slick.lifted.TableQuery

class DatabaseTableModuleSpec extends FunSpec {
  val tableModule = new DatabaseTableModule {}

  describe("DatabaseTableModule") {
    it("should have tables defined") {
      assert(tableModule.individualDataTable.isInstanceOf[TableQuery[IndividualDataTable]])
      assert(tableModule.individualLookupTable.isInstanceOf[TableQuery[IndividualLookupTable]])

      assert(tableModule.allTables.size === 2)
    }
  }
}
