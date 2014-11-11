package com.dslplatform.examples

import WorldWonders.Wonder

import com.typesafe.config.ConfigFactory
import org.scalactic.Equality
import org.scalatest.{ FeatureSpec, Matchers }

object RestSuite extends FeatureSpec with Matchers {
  private val config = ConfigFactory.load()

  val Host = config.getString("host")
  val Port = config.getInt("port")
  val BaseURL = "wonders"

  implicit val itemEquality = new Equality[Wonder] {
    override def areEqual(left: Wonder, right: Any): Boolean =
      right match {
        case item: Wonder =>
          (left.URI         == item.URI) &&
          (left.nativeNames == item.nativeNames) &&
          (left.isAncient   == item.isAncient) &&
          (left.englishName == item.englishName)
        case _ =>
          false
    }
  }
}

class RestSuite extends FeatureSpec with Matchers {
  import RestSuite._

  val rand = new RandHelper()
  val rest = new RequestHelper[Wonder](Host, Port)

  feature("Find") {
    scenario("Read one (item is created and then serched for)") {
      val item        = rand.nextItem()
      val createdItem = rest.post(BaseURL, item)
      val foundItem   = rest.get(BaseURL / createdItem.URI)
      createdItem shouldEqual foundItem
    }

    scenario("Find all items (several items are created, and then all are searched for)") {
      val itemList        = rand.nextItem repeated 5
      val createdItemList = itemList.map( i => rest.post(BaseURL, i))
      val allItemList     = rest.getList(BaseURL)
      createdItemList foreach { createdItem =>
        allItemList should contain (createdItem)
      }
    }

    scenario("Find missing (a non-exitent item is searched for)") {
      val item = rand.nextItem()
      val ex = the [Exception] thrownBy {
        rest.get(BaseURL / item.URI)
      }
      ex.getMessage should include ("""NGS\Client\Exception\NotFoundException""")
    }
  }

  feature("Update") {
    scenario("Update one (item is created and then updated)") {
      val item         = rand.nextItem()
      val createdItem  = rest.post(BaseURL, item)
      createdItem.englishName = rand.nextName()
      val updatedItem  = rest.put(BaseURL / createdItem.URI, createdItem)
      val verifiedItem = rest.get(BaseURL / updatedItem.URI)
      createdItem should not equal updatedItem
      updatedItem shouldEqual verifiedItem
    }

    scenario("Update missing (a non-exitent item is updated)") {
      val item = rand.nextItem()
      val ex = the [Exception] thrownBy {
        rest.put(BaseURL / item.URI, item)
      }
      ex.getMessage should include ("""NGS\Client\Exception\InvalidRequestException""")
    }
  }

  feature("Create") {
    scenario("Create one (item is created and then serched for)") {
      val item        = rand.nextItem()
      val createdItem = rest.post(BaseURL, item)
      val foundItem   = rest.get(BaseURL / createdItem.URI)
      createdItem shouldEqual foundItem
    }
  }

  feature("Delete") {
    scenario("Delete one (item is created and then deleted and then searched for)") {
      val item        = rand.nextItem()
      val createdItem = rest.post(BaseURL, item)
      val deletedItem = rest.delete(BaseURL / createdItem.URI)
      deletedItem shouldEqual createdItem
      val ex = the [Exception] thrownBy {
        val a = rest.get(BaseURL / deletedItem.URI)
      }
      ex.getMessage should include ("""NGS\Client\Exception\NotFoundException""")
    }

    scenario("Delete all (all items are deleted, and then searched for)") {
      val deletedItemlList = rest.deleteList(BaseURL)
      val foundItemList    = rest.getList(BaseURL)
      foundItemList should have size 0
    }
  }
}
