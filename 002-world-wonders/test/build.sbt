name := "002 [Test] World Wonders"

version := "0.0.0"

scalaVersion := "2.11.2"

scalacOptions ++= Seq(
    "-feature",
    "-language:postfixOps",
    "-language:implicitConversions"
)

unmanagedSourceDirectories in Compile :=
  sourceDirectory.value / "main" / "scala" ::
  sourceDirectory.value / "generated" / "scala" :: Nil

unmanagedResourceDirectories in Compile :=
  sourceDirectory.value / "main" / "resources" ::
  sourceDirectory.value / "generated" / "resources" :: Nil

unmanagedSourceDirectories in Test :=
  sourceDirectory.value / "test" / "scala" :: Nil

unmanagedResourceDirectories in Test :=
  sourceDirectory.value / "test" / "resources" :: Nil

libraryDependencies ++= Seq(
  "com.dslplatform"         %% "dsl-client-scala" % "0.9.0",
  "com.typesafe"            %  "config"           % "1.2.1",
  "net.databinder.dispatch" %% "dispatch-core"    % "0.11.2",
  "ch.qos.logback"          %  "logback-classic"  % "1.1.2" % "test",
  "org.scalatest"           %  "scalatest_2.11"   % "2.2.1" % "test"
)
