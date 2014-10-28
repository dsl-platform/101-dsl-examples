resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3.6")

addSbtPlugin("io.spray" % "sbt-revolver" % "0.7.2")

addSbtPlugin("org.xerial.sbt" % "sbt-pack" % "0.6.2")

resolvers += "Element Releases" at "http://repo.element.hr/nexus/content/repositories/releases/"

addSbtPlugin("hr.element.xsbt" % "xsbt-sh" % "0.1.0")
