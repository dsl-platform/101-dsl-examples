package com.dslplatform.examples

import WorldWonders.Wonder
import akka.actor._

import com.dslplatform.api.client.JsonSerialization
import com.dslplatform.api.patterns.PersistableRepository

import org.slf4j.Logger

import scala.concurrent.Future
import scala.io.StdIn
import scala.util._

object Main extends App {
  system.actorOf(Props[Main]) ! Prompt
}

case object Prompt

class Main extends Actor {
  val logger = locator.resolve[Logger]
  val wonderRepository = locator.resolveUnsafe[PersistableRepository[Wonder]]
  val jsonSerialization = locator.resolve[JsonSerialization]

  implicit class RichFuture[T](fut: Future[T]) {
    def handleError(success: T => Unit) =
      fut onComplete {
        case Success(s) =>
          success(s)

        case Failure(f) =>
          f.printStackTrace(System.out)
          self ! Prompt
      }
  }

  def printTable(wonders: Seq[Wonder]) = {
    println(Table(
      Row("Type", "English name", "Native names"),
      wonders map { wonder => Row(
        if (wonder.isAncient) "Ancient" else "New",
        wonder.englishName,
        wonder.nativeNames.mkString(", ")
      )}
    ))
    self ! Prompt
  }

  def receive = {
    case Prompt =>
      print("Enter command (h for help): ")
      val command = StdIn.readLine().trim
      self ! command

    case "h" =>
      println("""Usage:
  h = display this help
  q = quit program

Querying the catalogue:
  c = count number of world wonders
  l = list world wonders using ASCII art

Adding wonders:
  a = add an "ancient world" wonder
  n = add a "new world" wonder

Import / cleanup:
  x = delete all wonders
  i = import wonders from resources
""")
      self ! Prompt

    case "q" =>
      shutdown()
      sys.exit(0)

    case "c" =>
      wonderRepository.count(None) handleError { count =>
        println("There are " + count + " world wonder(s)")
        self ! Prompt
      }

    case "l" =>
      wonderRepository.search(None) handleError printTable

    case "a" =>
      self ! ("new", true)

    case "n" =>
      self ! ("new", false)

    case ("new", isAncient: Boolean) =>
      val tipe = if (isAncient) "ancient" else "new"
      print(s"Enter the English name of the $tipe world wonder: ")
      val englishName = StdIn.readLine().trim

      println("Checking if such wonder already exists ...")
      wonderRepository.find(Seq(englishName)) handleError { lookup =>
        if (lookup.nonEmpty) {
          println("That wonder already exist, please enter a new one!")
          self ! ("new", isAncient)
        } else {
          print("Enter native name(s) for " + englishName + ": ")
          val nativeNames = StdIn.readLine().split(",+").map(_.trim).filter(_.nonEmpty)

          val newWonder = Wonder(
            englishName = englishName,
            nativeNames = nativeNames.toList,
            isAncient = isAncient
          )

          wonderRepository.insert(newWonder) handleError { _ =>
            printTable(Seq(newWonder))
          }
        }
      }

    case "x" =>
      wonderRepository.search(None) handleError { wonders =>
        wonderRepository.delete(wonders) handleError { _ =>
          printTable(Seq())
        }
      }

    case "i" =>
      val is = getClass.getResourceAsStream("/wonders.json")
      // Horribly, horribly inefficient.
      // Use Apache commons instead: org.apache.commons.io.IOUtils.toByteArray(is)
      // Or Scala IO from incubator: scalax.io.Resource.fromInputStream(in).byteArray
      val resource = Iterator continually is.read takeWhile (-1 !=) map (_.toByte) toArray

      val wonders = jsonSerialization.deserialize[Array[Wonder]](resource)
      wonderRepository.insert(wonders) onComplete {
        case Success(_) =>
          println("Imported " + wonders.length + " wonders!")
          printTable(wonders)

        case Failure(_) =>
          println("Could not import wonders, probably due to duplicates.")
          println("Please drop the existing entries first (x).")
          self ! Prompt
      }

    case x =>
      System.out.println("Invalid command: " + x)
      self ! Prompt
  }
}
