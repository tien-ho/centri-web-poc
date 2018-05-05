// See https://wiki.audaxhealth.com/display/ENG/Build+Structure#BuildStructure-Localconfiguration
credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

resolvers += Resolver.url("Rally Plugin Releases", url("https://artifacts.werally.in/artifactory/ivy-plugins-release"))(Resolver.ivyStylePatterns)
resolvers += "Artifactory" at "https://artifacts.werally.in/artifactory/maven-central/"
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.rallyhealth" %% "rally-versioning" % "1.2.0")

addSbtPlugin("com.rallyhealth" %% "rally-sbt-plugin" % "0.5.0")

addSbtPlugin("com.rallyhealth" % "rally-kamon-sbt-plugin" % "0.0.1")

addSbtPlugin("com.rallyhealth.sbt" %% "rally-docker-sbt-plugin" % "1.4.0")

addSbtPlugin("com.rallyhealth.sbt" %% "rally-shading-sbt-plugin" % "1.0.2")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.13.0")

addSbtPlugin("com.julianpeeters" % "sbt-avrohugger" % "2.0.0-RC4")
// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.13")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.0")
