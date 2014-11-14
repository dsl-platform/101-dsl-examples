package com.dslplatform.examples

import WorldWonders.Wonder
import com.typesafe.config.ConfigFactory
import org.scalactic.Equality
import org.scalatest.{ FeatureSpec, Matchers }
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import sun.misc.IOUtils

trait SuiteBase extends FeatureSpec with Matchers{
  private val config = ConfigFactory.load()

  val Host = config.getString("host")
  val Port = config.getInt("port")
  val BaseURL = "wonders"

  implicit val wonderEquality = new Equality[Wonder] {
    override def areEqual(left: Wonder, right: Any): Boolean =
      right match {
        case wonder: Wonder =>
          (left.URI         == wonder.URI) &&
          (left.isAncient   == wonder.isAncient) &&
          (left.englishName == wonder.englishName) &&
          (left.nativeNames == wonder.nativeNames)
        case _ =>
          false
    }
  }

  val defaultWonderList = {
    val is   = classOf[SuiteBase].getResourceAsStream("/wonders.json")
    val body = IOUtils.readFully(is, -1, true)
    jsonSerialization.deserializeList[Wonder](body)
  }
}
