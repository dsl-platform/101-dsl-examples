package com.dslplatform.examples

import WorldWonders.Wonder

class RestSuite extends SuiteBase {
  val rand = new RandHelper()
  val rest = new RequestHelper[Wonder](Host, Port)

  feature("Find") {
    scenario("Read one (wonder is created and then serched for)") {
      val wonder        = rand.nextWonder()
      val createdWonder = rest.post(BaseURL, wonder)
      val foundWonder   = rest.get(BaseURL / createdWonder.URI)
      createdWonder shouldEqual foundWonder
    }

    scenario("Find all wonders (several wonders are created, and then all are searched for)") {
      val wonderList        = rand.nextWonder repeated 5
      val createdWonderList = wonderList.map( i => rest.post(BaseURL, i))
      val allWonderList     = rest.getList(BaseURL)
      createdWonderList foreach { createdWonder =>
        allWonderList should contain (createdWonder)
      }
    }

    scenario("Find missing (a non-exitent wonder is searched for)") {
      val wonder = rand.nextWonder()
      val ex = the [Exception] thrownBy {
        rest.get(BaseURL / wonder.URI)
      }
      ex.getMessage should include ("""NGS\Client\Exception\NotFoundException""")
    }
  }

  feature("Update") {
    scenario("Update one (wonder is created and then updated)") {
      val wonder        = rand.nextWonder()
      val createdWonder = rest.post(BaseURL, wonder)
      createdWonder.englishName = rand.nextName()
      val updatedWonder  = rest.put(BaseURL / createdWonder.URI, createdWonder)
      val verifiedWonder = rest.get(BaseURL / updatedWonder.URI)
      createdWonder should not equal updatedWonder
      updatedWonder shouldEqual verifiedWonder
    }

    scenario("Update missing (a non-exitent wonder is updated)") {
      val wonder = rand.nextWonder()
      val ex = the [Exception] thrownBy {
        rest.put(BaseURL / wonder.URI, wonder)
      }
      ex.getMessage should include ("""NGS\Client\Exception\InvalidRequestException""")
    }
  }

  feature("Create") {
    scenario("Create one (wonder is created and then serched for)") {
      val wonder        = rand.nextWonder()
      val createdWonder = rest.post(BaseURL, wonder)
      val foundWonder   = rest.get(BaseURL / createdWonder.URI)
      createdWonder shouldEqual foundWonder
    }
  }

  feature("Delete") {
    scenario("Delete one (wonder is created and then deleted and then searched for)") {
      val wonder        = rand.nextWonder()
      val createdWonder = rest.post(BaseURL, wonder)
      val deletedWonder = rest.delete(BaseURL / createdWonder.URI)
      deletedWonder shouldEqual createdWonder
      val ex = the [Exception] thrownBy {
        val a = rest.get(BaseURL / deletedWonder.URI)
      }
      ex.getMessage should include ("""NGS\Client\Exception\NotFoundException""")
    }

    scenario("Delete all (all wonders are deleted, and then searched for)") {
      val deletedWonderlList = rest.deleteList(BaseURL)
      val foundWonderList    = rest.getList(BaseURL)
      foundWonderList should have size 0
    }
  }
}
