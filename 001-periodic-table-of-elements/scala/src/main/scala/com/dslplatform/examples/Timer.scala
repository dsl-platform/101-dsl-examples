package com.dslplatform.examples

class Timer {
  private var startAt: Long = 0

  private def getTime() = System.currentTimeMillis

  def reset() {
    startAt = getTime
  }

  def elapsed(): Long = {
    getTime - startAt
  }
}
