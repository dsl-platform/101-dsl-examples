name := "002 [Scala] World Wonders"

version := "0.0.0"

scalaVersion := "2.11.2"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

unmanagedSourceDirectories in Compile ++= Seq(
  baseDirectory.value / "app" / "models" / "generated" / "scala",
  baseDirectory.value / "app" / "models" / "main" / "scala"
)

unmanagedResourceDirectories in Compile ++= Seq(
  baseDirectory.value / "app" / "models" / "generated" / "resources"
)

libraryDependencies ++= Seq(
  "com.dslplatform" %% "dsl-client-scala" % "0.9.0"
, "ch.qos.logback" % "logback-classic" % "1.1.2"
, "org.scalatest" %% "scalatest" % "2.2.2" % "test"
)
