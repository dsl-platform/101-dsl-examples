package com.dslplatform.examples

import WorldWonders.Wonder

class UtilSuite extends SuiteBase {
  val rand = new RandHelper()
  val rest = new RequestHelper[Wonder](Host, Port)

  feature("Other") {
    scenario("Reset defaults") {
      val resetWonderList = rest.getList("reset")
      resetWonderList.size shouldEqual defaultWonderList.size
      resetWonderList.zip(defaultWonderList).foreach {
        case (resetWonder, defaultWonder) =>
          resetWonder should have (
              'isAncient   (defaultWonder.isAncient),
              'englishName (defaultWonder.englishName),
              'nativeNames (defaultWonder.nativeNames),
              'imageURL    (defaultWonder.imageURL)
          )
      }
    }

    scenario("Options") {
      val response = rest.options("")
      response shouldBe ""
    }

    scenario("Random image") {
      val response = rest.rest.get("randomimg")
      val status   = response.status
      val message  = new String(response.body)

      status shouldBe 200
      message should include ("\"title\":")
      message should include ("\"url\":")
    }
  }
}
