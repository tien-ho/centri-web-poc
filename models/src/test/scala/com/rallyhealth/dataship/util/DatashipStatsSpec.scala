package com.rallyhealth.dataship.util

import java.time.Instant

import org.mockito._
import org.mockito.Mockito.times
import org.scalatest.FlatSpec
import org.scalatest.mockito.MockitoSugar

class DatashipStatsSpec extends FlatSpec with MockitoSugar {

  class TestDatashipStats extends DatashipStats {
    val statPath = Seq("testDataship")
  }

  "emitSuccess" should "emit the correct key for a successful operation" in {
    val spyTestDatashipStats: TestDatashipStats = Mockito.spy(new TestDatashipStats())
    val start = Instant.now().toEpochMilli
    spyTestDatashipStats.emitSuccess(Some(start), Some("testoperation"))
    Mockito.verify(spyTestDatashipStats, times(1)).statIt("testdataship.success.testoperation", Some(start))
  }

  "emitError" should "emit the correct key for a failed operation" in {
    val spyTestDatashipStats: TestDatashipStats = Mockito.spy(new TestDatashipStats())
    val start = Instant.now().toEpochMilli
    spyTestDatashipStats.emitError(Some(start), Some("testoperation"))
    Mockito.verify(spyTestDatashipStats, times(1)).statIt("testdataship.error.testoperation", Some(start))
  }

  "emitAttempt" should "emit the correct key for a attempted operation" in {
    val spyTestDatashipStats: TestDatashipStats = Mockito.spy(new TestDatashipStats())
    val start = Instant.now().toEpochMilli
    spyTestDatashipStats.emitAttempt(Some("testoperation"))
    Mockito.verify(spyTestDatashipStats, times(1)).statIt("testdataship.attempt.testoperation", None)
  }

}
