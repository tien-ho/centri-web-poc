

import com.rallyhealth.sbt.shading.ShadingImplicits._
import sbt._

object Dependencies {

  protected def concat(configurations: Iterable[Configuration]): String = configurations.mkString(";")

  private val careStats = "4.4.0"
  private val jsonOps = "1.5.0"
  private val jsonTests = "1.5.0"
  private val libEnigma = "4.1.0"
  private val libIlluminatiVersion = "9.2.3"
  private val libPostgresVersion = "2.0.0"
  private val libRallyWSClientVersion = "16.3.0"
  private val mempe = "8.0.0"
  private val dreamlinerVersion = "8.4.0"
  private val mockitoVersion = "2.7.20"
  private val playJsonNamingVersion = "1.1.0"
  private val playModuleOpsVersion = "2.0.0"
  private val playSlick = "2.1.0"
  private val playTestOpsVersion = "1.0.0"
  private val scalaCheck = "1.5.0"
  private val scalaTest = "3.0.1"
  private val spartan = "2.4.0"
  private val kamonVersion = "0.6.7"

  object Rally {

    // LIBRARY DEPENDENCIES | Dos and Don'ts: https://wiki.audaxhealth.com/x/Wp_8AQ
    val libCareStatsDatadog = "com.rallyhealth.core" %% "lib-carestats-datadog-v4" % careStats
    val libCareStatsPlay25 = "com.rallyhealth.core" %% "lib-carestats-play25-v4" % careStats
    val libEnigmaPlay25 = "com.rallyhealth.core" %% "lib-enigma-play25" % libEnigma shaded
    val libIlluminatiPlay25 = "com.rallyhealth.core" %% "lib-illuminati-play25-v9" % libIlluminatiVersion
    val libDreamlinerModel = "com.rallyhealth.dreamliner" %% "dreamliner-models" % dreamlinerVersion
    val libOptumMempe = "com.rallyhealth.optum" %% "lib-optum-mempe-play25-v8" % mempe
    val libPostgres = "com.rallyhealth.core" %% "lib-postgres-play25-bundle" % libPostgresVersion % "compile;test->test" shaded
    val libSpartanPlay25 = "com.rallyhealth.core" %% "lib-spartan-play25-json-v2" % "2.5.0"
    val libSpartanPlay25Json = "com.rallyhealth.core" %% "lib-spartan-play25-json" % spartan shaded
    val playJsonOps = "com.rallyhealth" %% "play-json-ops-25" % jsonOps
    val playJsonTests = "com.rallyhealth" %% "play-json-tests-25" % jsonTests intransitive ()
    val playModuleOpsMonitor = "com.rallyhealth.core" %% "play-module-ops-monitor" % playModuleOpsVersion
    val playTestOps = "com.rallyhealth" %% "play25-test-ops-core" % playTestOpsVersion
    val scalacheckOps = "com.rallyhealth" %% "scalacheck-ops_1-13" % scalaCheck
    val libCorrelationKamonLogging = "com.rallyhealth.correlation" %% "lib-correlation-kamon-logging" % "0.1.1"
    val libHttpInterceptorPlay25Bundle = "com.rallyhealth.interceptor" %% s"lib-http-interceptor-play25-bundle" % "0.1.0"

    object TestDep {
    }
  }

  object Ext {

    // external dependencies, keep alpha
    val aspectJWeaver = "org.aspectj" % "aspectjweaver" % "1.8.10"
    val kamonAkka = "io.kamon" %% "kamon-akka-2.5" % kamonVersion
    val kamonCore = "io.kamon" %% "kamon-core" % kamonVersion
    val kamonPlay = "io.kamon" %% "kamon-play-2.5" % kamonVersion
    val akkaMockScheduler = "com.miguno.akka" %% "akka-mock-scheduler" % "0.5.1"
    val apacheCommons = "org.apache.commons" % "commons-lang3" % "3.5.0"
    val logback = "ch.qos.logback" % "logback-classic" % "1.2.2"
    // logback-mesos.xml makes use of the newer features.
    val mockito = "org.mockito" % "mockito-core" % mockitoVersion % "test"
    val playJsonExt = "ai.x" %% "play-json-extensions" % "0.9.0"
    val playJsonNaming = "com.github.tototoshi" %% "play-json-naming" % playJsonNamingVersion
    val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
    val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
    val scalaTestStandAlone = "org.scalatest" %% "scalatest" % scalaTest % "test"
    val scalaTestPlus = "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0"
    val macWireMacros = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
    val macWireUtil = "com.softwaremill.macwire" %% "util" % "2.3.0"
    val macWireProxy = "com.softwaremill.macwire" %% "proxy" % "2.3.0"
    val libLogbackClassic = "ch.qos.logback" % "logback-classic" % "1.2.3"
    val h2Database = "com.h2database" % "h2" % "1.4.192" % "test"
    val playSlickLib = "com.typesafe.play" %% "play-slick" % playSlick
    val playSlickEvolutions = "com.typesafe.play" %% "play-slick-evolutions" % playSlick
    val postgresDriver = "org.postgresql" % "postgresql" % "42.1.4"
    var apacheKafka = "org.apache.kafka" %% "kafka" % "1.0.1"
    val apacheAvro = "org.apache.avro" % "avro" % "1.8.2"
    var confluentAvro = "io.confluent" % "kafka-avro-serializer" % "4.0.0"
  }
}
