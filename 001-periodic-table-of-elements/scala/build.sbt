name := "001 [Scala] Periodic Table of Elements"

version := "0.0.0"

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
, "ch.qos.logback" % "logback-classic" % "1.1.2"
)

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource
