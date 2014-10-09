name := "World Wonders"

version := "0.1.0"

scalaVersion := "2.11.2"

unmanagedSourceDirectories in Compile +=
  sourceDirectory.value / "generated" / "scala"

unmanagedResourceDirectories in Compile +=
  sourceDirectory.value / "generated" / "resources"

libraryDependencies ++= Seq(
  "com.dslplatform" %% "dsl-client-scala" % "0.9.0"
, "com.typesafe.akka" %% "akka-actor" % "2.3.6"
, "com.typesafe.akka" %% "akka-slf4j" % "2.3.6"
, "ch.qos.logback" % "logback-classic" % "1.1.2"
, "org.scalatest" %% "scalatest" % "2.2.2" % "test"
)
