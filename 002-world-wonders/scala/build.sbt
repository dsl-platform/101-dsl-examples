packAutoSettings

Revolver.settings

name := "002 [Scala] World Wonders"

version := "0.1.0"

scalaVersion := "2.11.2"

lazy val root = project in file(".") enablePlugins(PlayScala)

packMain := Map("main" -> "play.core.server.NettyServer")

libraryDependencies ++= Seq(
  "com.dslplatform" %% "dsl-client-scala" % "0.9.2"
, "ch.qos.logback" % "logback-classic" % "1.1.2"
, "org.scalatest" %% "scalatest" % "2.2.2" % "test"
)
