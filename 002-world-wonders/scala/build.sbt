name := "002 [Scala] World Wonders"

version := "0.1.0"

scalaVersion := "2.11.2"

unmanagedSourceDirectories in Compile :=
  sourceDirectory.value / "main" / "scala" ::
  sourceDirectory.value / "generated" / "scala" ::
  Nil

unmanagedResourceDirectories in Compile :=
  sourceDirectory.value / "main" / "resources" ::
  sourceDirectory.value / "generated" / "resources" ::
  Nil

unmanagedSourceDirectories in Test := Nil

unmanagedResourceDirectories in Test := Nil

libraryDependencies ++= Seq(
  "com.dslplatform" %% "dsl-client-scala" % "0.9.0"
, "com.typesafe.akka" %% "akka-actor" % "2.3.6"
, "com.typesafe.akka" %% "akka-slf4j" % "2.3.6"
, "ch.qos.logback" % "logback-classic" % "1.1.2"
, "org.scalatest" %% "scalatest" % "2.2.2" % "test"
)
