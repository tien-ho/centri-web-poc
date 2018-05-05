package com.rallyhealth.dataship.util

import java.time.Instant

import com.rallyhealth.stats.v4.{KeyPrefixedStats, Stats}

/**
  * Helps with building statistics
  */
trait DatashipStats {

  protected val stats: Stats = KeyPrefixedStats(Stats, "dataship")

  def statPath: Seq[String]

  /**
    * Count per provided fullKey and optionally send duration if start time is provided
    * @param fullKey full key as string
    * @param maybeStartTime optional start time variable
    */
  def statIt(fullKey: String, maybeStartTime: Option[Long]) = {
    stats.inc(fullKey)
    maybeStartTime match {
      case Some(start) => emitTiming(fullKey, start)
      case None => //skip timing it
    }
  }

  /**
    * Send duration per fullKey
    * @param fullKey
    * @param start
    */
  def emitTiming(fullKey: String, start: Long) =
    stats.timing(stringify(combine(Seq(fullKey), Duration, None)), (Instant.now().toEpochMilli - start).toInt)

  /**
    * Stat on error with count and optional duration
    * @param maybeStartTime
    * @param mayEndKey
    */
  def emitError(maybeStartTime: Option[Long], mayEndKey: Option[String] = None) =
    statIt(stringify(combine(statPath, Error, mayEndKey)), maybeStartTime)

  /**
    * Stat on error with count and optional duration
    * @param maybeStartTime
    * @param mayEndKey
    */
  def emitSuccess(maybeStartTime: Option[Long], mayEndKey: Option[String] = None) =
    statIt(stringify(combine(statPath, Success, mayEndKey)), maybeStartTime)

  /**
    * Stat on attempts
    */
  def emitAttempt(mayEndKey: Option[String] = None) =
    statIt(stringify(combine(statPath, Attempt, mayEndKey)), None)

  /**
    * utility class to combine path, type, tag into a full key
    * @param path
    * @param `type`
    * @param mayEndTag
    * @return
    */
  private def combine(path: Seq[String], `type`: StatType, mayEndTag: Option[String]): Seq[String] = {
    mayEndTag match {
      case Some(endTag) => path :+ `type`.toString :+ endTag
      case None => path :+ `type`.toString
    }
  }

  /**
    * Just concatenate sequence of strings by comma
    * @param path
    * @return
    */
  private def stringify(path: Seq[String]) = path.map(_.trim.toLowerCase).mkString(".")

  private sealed trait StatType
  private case object Success extends StatType
  private case object Attempt extends StatType
  private case object Error extends StatType
  private case object Duration extends StatType

}

object StatsConstants {
  val mempeservice = Seq("mempeservice")
}
