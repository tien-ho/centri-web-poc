import sbt.Keys._
import play.sbt.PlayImport
import Dependencies._
import com.rallyhealth.sbt.{RallyDockerSbtPlugin, RallyKamonPlugin}

name := "centri-poc"

organizationName in ThisBuild := "Rally Health"
organization in ThisBuild := "com.rallyhealth.centri"

rallyVersioningSnapshotLowerBound in ThisBuild := "1.0.0"

resolvers in ThisBuild += rallyArtifactoryLibSnapshotsResolver.value

//NOTE: remove the package model from this regex once they are functional and tests are written
val standardPlayCoverageExclude = "<empty>;Reverse.*;router\\.*;.*models.*"

scalacOptions in ThisBuild := Seq(
  "-Xlint",
  "-feature",
  "-deprecation:false",
  "-unchecked",
  "-Xfuture",
  "-Ywarn-nullary-override",
  "-Ywarn-inaccessible",
  "-Ywarn-adapted-args"
)

lazy val root = (project in file("."))
      .aggregate(web, reader, models)

//note this has no dependencies except the one pulled in from the SbtAvro plugin
lazy val avro_schemas = project.in(file("avro_schemas"))
  .settings(
    name := "lib-centri-avro-schemas",
    organization := "com.rallyhealth.centri",
    libraryDependencies ++= Seq(
      Ext.logback,
      Ext.libLogbackClassic,
      Ext.apacheKafka,
      Ext.apacheAvro,
      Ext.confluentAvro
    ),
    sourceGenerators in Compile += (avroScalaGenerateSpecific in Compile).taskValue
  )

lazy val models = (project in file("models"))
  .settings(
    name := "centri-models",
    coverageEnabled in Test := true,
    publishArtifact := true,
    publishArtifact in Test := false,
    // Workaround to prevent race conditions in DB tests
    parallelExecution in Test := false,
    libraryDependencies ++= Seq(
      Ext.macWireMacros,
      Ext.macWireProxy,
      Ext.macWireUtil,
      Ext.mockito,
      Ext.playJsonExt,
      Ext.playJsonNaming,
      Ext.playSlickLib,
      Ext.scalaCheck,
      Ext.scalaLogging,
      Ext.scalaTestPlus,
      PlayImport.jdbc,
      Rally.libSpartanPlay25Json,
      Rally.libCareStatsDatadog,
      Rally.libCareStatsPlay25,
      Rally.libEnigmaPlay25,
      Rally.libIlluminatiPlay25,
      Rally.libPostgres,
      Rally.scalacheckOps
    )
  )

lazy val reader = (project in file("reader"))
  .enablePlugins(RallyDockerSbtPlugin)
  .enablePlugins(RallyKamonPlugin)
  .enablePlugins(PlayScala)
  .dependsOn(
    avro_schemas, models
  )
  .configs(config("it") extend Test)
  .settings(Defaults.itSettings: _*)
  .settings(
    name := "centri-reader",
    rallyDockerAppImageName := "core/centri-reader",
    // check out - https://gist.github.com/htmldoug/254af1b3fc34c4159f0d6839116d354b
    rallyDockerAppJavaAgents += JavaAgent(Ext.aspectJWeaver, autoEnable = true),
    coverageEnabled in Test := true,
    publishArtifact := true,
    publishArtifact in Test := false,
    coverageExcludedPackages := standardPlayCoverageExclude, // all excludes get concatenated here
    libraryDependencies ++= Seq(
      // app dependencies, keep alpha
      Ext.akkaMockScheduler,
      Ext.logback,
      Ext.macWireMacros,
      Ext.macWireProxy,
      Ext.macWireUtil,
      Ext.scalaLogging,
      Ext.scalaTestPlus,
      Ext.mockito,
      PlayImport.jdbc,
      PlayImport.evolutions,
      Rally.libHttpInterceptorPlay25Bundle,
      Rally.libCorrelationKamonLogging,
      Rally.libCareStatsDatadog,
      Rally.libCareStatsPlay25,
      Rally.libEnigmaPlay25,
      Rally.libIlluminatiPlay25,
      Rally.libOptumMempe,
      Rally.libPostgres,
      Rally.libSpartanPlay25Json,
      Rally.playJsonTests,
      Rally.playTestOps,
      Ext.postgresDriver,
      Rally.playModuleOpsMonitor,
      Rally.scalacheckOps,
      Ext.libLogbackClassic,
      Ext.h2Database,
      Ext.apacheKafka,
      Ext.apacheAvro,
      Ext.confluentAvro
    )
  )

lazy val web = (project in file("web"))
  .enablePlugins(RallyDockerSbtPlugin)
  .enablePlugins(RallyKamonPlugin)
  .enablePlugins(PlayScala)
  .dependsOn(
    avro_schemas, models
  )
  .aggregate(
    models
  )
  .configs(config("it") extend Test)
  .settings(Defaults.itSettings: _*)
  .settings(
    name := "centri",
    rallyDockerAppImageName := "core/centri",
    // check out - https://gist.github.com/htmldoug/254af1b3fc34c4159f0d6839116d354b
    rallyDockerAppJavaAgents += JavaAgent(Ext.aspectJWeaver, autoEnable = true),
    coverageEnabled in Test := true,
    publishArtifact := true,
    publishArtifact in Test := false,
    coverageExcludedPackages := standardPlayCoverageExclude, // all excludes get concatenated here
    libraryDependencies ++= Seq(
      // app dependencies, keep alpha
      Ext.akkaMockScheduler,
      Ext.logback,
      Ext.macWireMacros,
      Ext.macWireProxy,
      Ext.macWireUtil,
      Ext.mockito,
      Ext.playSlickLib,
      Ext.scalaLogging,
      Ext.scalaTestPlus,
      PlayImport.jdbc,
      PlayImport.evolutions,
      Rally.libHttpInterceptorPlay25Bundle,
      Rally.libCorrelationKamonLogging,
      Rally.libCareStatsDatadog,
      Rally.libCareStatsPlay25,
      Rally.libDreamlinerModel,
      Rally.libEnigmaPlay25,
      Rally.libIlluminatiPlay25,
      Rally.libOptumMempe,
      Rally.libPostgres,
      Rally.libSpartanPlay25Json,
      Rally.playJsonTests,
      Rally.playTestOps,
      Ext.postgresDriver,
      Rally.playModuleOpsMonitor,
      Rally.scalacheckOps,
      Ext.libLogbackClassic,
      Ext.h2Database
    )
  )

scalaVersion := "2.11.11"
updateOptions := updateOptions.value.withLatestSnapshots(false)
