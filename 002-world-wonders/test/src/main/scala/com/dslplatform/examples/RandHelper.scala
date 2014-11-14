package com.dslplatform.examples

import WorldWonders.Wonder

import java.util.UUID
import scala.util.Random

class RandHelper {
  val r = new Random

  def nextNativeNameCount(max: Int) =
    if (r.nextBoolean()) {
      r.nextInt(max) + 1
    } else {
      0
    }

  def nextName() = UUID.randomUUID().toString

  def nextNativeNameList(maxCount: Int) =
    nextName().repeated(nextNativeNameCount(maxCount)).toList

  def nextIsAncient() = r.nextBoolean()

  def nextWonder() = {
    val maxCount = 5
    Wonder(
        englishName = nextName(),
        nativeNames = nextNativeNameList(maxCount),
        isAncient = nextIsAncient()
    )
  }
}
