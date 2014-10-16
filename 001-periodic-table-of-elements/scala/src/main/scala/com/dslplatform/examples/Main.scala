package com.dslplatform.examples

import com.dslplatform.api.client.JsonSerialization
import com.dslplatform.examples.PeriodicTable.Element
import scala.annotation.tailrec
import scala.io.StdIn.readLine

object Main {
  def main(args: Array[String]) {
    new Main().runREPL()
    shutdown()
    println("Program exiting.")
  }
}


class Main() {
  private val jsonSerialization = locator.resolve[JsonSerialization]

  // Timer is used to measure duration of commands.
  // We use a mutable timer to simplify things. It could, of course, be done
  // immutably, but it would be unnecessarily complicated.
  private val timer = new Timer

  /** Prints a list of elements using ASCII table */
  def printElements(elementList: Seq[Element]) {
    val headerTable = Seq(Seq("Number", "Name"))
    val elementTable = elementList map { element =>
      Seq(element.number.toString, element.name)
    }

    val table = headerTable ++ elementTable
    println(AsciiTable.make(table))
  }


  /** Main REPL loop
   *  Runs until 'q' is entered, or an error occurs
   */
  @tailrec
  final def runREPL() {
    val isEnd = try {
      // Read clean the command.
      print("Enter command (h for help): ")
      val line = readLine()
      val command = line.trim()

      // Start the timer.
      timer.reset()

      // Parse and execute the command.
      // Return value indicates weather to end the loop (it's stored into isEnd
      // above).
      command match {
        case "h" =>
          execHelp()
          false
        case "q" =>
          true
        case "c" =>
          execCount()
          false
        case "l" =>
          execList()
          false
        case "a" =>
          execAdd()
          false
        case "e" =>
          execEdit()
          false
        case "x" =>
          execDeleteAll()
          false
        case "i" =>
          execInsert()
          false
        case _ =>
          println(s"Invalid command: $command")
          false
      }
    } catch {
      case e: Exception =>
        e.printStackTrace(System.out)
        // End on error
        true
    }

    // Read and print elapsed time from timer reset.
    val duration = timer.elapsed()
    println(s"Took: $duration ms")

    // If end, return, else call REPL again.
    if (!isEnd) {
      runREPL
    } else {
      new RuntimeException().printStackTrace()
      ()
    }
  }


  /** Prints the help. */
  private def execHelp() {
    println(
        """|Usage:
           |  h = display this help
           |  q = quit program
           |
           |Querying the catalogue:
           |  c = count number of elements
           |  l = list elements using ASCII art
           |
           |Adding and editing:
           |  a = add an element
           |  e = edit an element
           |
           |Import / cleanup:
           |  x = delete all elements
           |  i = import elements from resources
           |""".stripMargin)
  }


  /** Counts number of Elements stored in the database. */
  private def execCount() {
    val count = Element.count
    if (count == 1) {
        println("There is only 1 element")
    } else {
        println(s"There are $count elements")
    }
  }


  /** Prints a list of stored elements. */
  private def execList() {
    val elements = Element.findAll
    printElements(elements)
  }


  /** Adds an element to the database. */
  private def execAdd() = {
    // This returns an integer which does not correspond to any element number
    // in the database.
    val number = readElementNumber("Enter the new element number: ", false)

    // Read and clean the name for new element.
    print(s"""Enter name for element $number: """)
    val name = readLine().trim

    // We want to measure only the time it takes to execute the command, so
    // reset the timer again after user is finished with input.
    timer.reset()

    // Finally, create and persist the new element (and print it).
    val newElement = Element(number, name)
    newElement.create
    printElements(Seq(newElement))
  }


  private def execEdit() = {
    // This returns an integer which corresponds to an element number in the
    // database.
    val number = readElementNumber("Enter the existing element number: ", true)

    // Technically, we already fetched an element in `readElementNumber`, but
    // it would be complicated to return it. It's much easier to read it again.
    val element = Element.find(number.toString)

    // Read and clean the new name for the element.
    print(s"""Enter new name for element $number (${ element.name }): """)
    val newName = readLine().trim

    // We want to measure only the time it takes to execute the command, so
    // reset the timer again after user is finished with input.
    timer.reset()

    // Finally, update and persist the new element (and print it).
    element.name = newName
    element.update
    printElements(Seq(element))
  }


  /** Deletes all elements from the database. */
  private def execDeleteAll() {
    // In order to delete all entries, we simply do a search for all of them and
    // then delete them.
    val elements = Element.findAll

    // NOTE: This will delete each element individually, and send a separate
    // query towards the database for each element. For 112 elements, it can
    // take a while.
    // See example 2: World Wonders for a better way to delete multiple
    // aggregates.
    elements foreach { element =>
      element.delete
    }
    printElements(Seq.empty)
  }


  /** Reads a list of elements form a resource file, and inserts them all. */
  private def execInsert() {
    // Get the resource stream.
    val is = Main.getClass.getResourceAsStream("/elements.json")

    // Horribly, horribly inefficient.
    // Use Apache commons instead: org.apache.commons.io.IOUtils.toByteArray(is)
    // Or Scala IO from incubator: scalax.io.Resource.fromInputStream(in).byteArray
    val resource = Iterator continually is.read takeWhile (-1 !=) map (_.toByte) toArray

    // This deserializes the resource byte array into a Array of elements.
    val elements = jsonSerialization.deserialize[Array[Element]](resource)

    try {
      // NOTE: This will insert each element individually, and send a separate
      // query towards the database for each element. For 112 elements, it can
      // take a while.
      // See example 2: World Wonders for a better way to insert multiple
      // aggregates.
      elements foreach { element =>
        element.create
      }
      println(s"Imported ${ elements.size } elements!")
      printElements(elements)
    } catch {
      case e: Exception =>
        println("Could not import elements, probably due to duplicates.")
        println("Please drop the existing entries first (x).")
    }
  }


  /** A helper function which reads user input until:
   *    1) A valid integer is entered
   *    2) Depending on `elementMustExist` parameter, the element with
   *       corresponding number either must be found, or must be missing from
   *       the database.
   */
  @tailrec
  private def readElementNumber(prompt: String, elementMustExist: Boolean): Int = {
    // First get a valid integer.
    val number = readNumber(prompt)
    println("Checking if that element already exists...")
    // Find on a collection will return an empty collection if none of those
    // elements could be found.
    // Find on a single element will throw an exception if that element could
    // not be found.
    // Thus, we issue a find on a collection of just one element, expecting to
    // get 0 or 1 results.
    val lookup = Element.find(Seq(number.toString))
    val elementExists = !lookup.isEmpty

    (elementExists, elementMustExist) match {
      case (true, false) =>
        // Element was found, but `elementMustExist` specifies that it must
        // not exist (error, so try again).
        println("That element already exist, please enter a new one!")
        readElementNumber(prompt, elementMustExist)
      case (false, true) =>
        // Element was not found, but `elementMustExist` specifies that it
        // must exist (error, so try again).
        println("That element does not exist, please enter one that does!")
        readElementNumber(prompt, elementMustExist)
      case _ =>
        // Either:
        //  element was found and it must exists or,
        //  element was not found and it must not exist.
        // Success, so return.
        number
    }
  }


  /** A heper method which reads user input until a valid integer is entered. */
  @tailrec
  private def readNumber(prompt: String): Int = {
    print(prompt)
    val numberRaw = readLine().trim
    try {
      Integer.parseInt(numberRaw)
    } catch {
      case e: NumberFormatException =>
        println(s""""$numberRaw" does not seem to look like a number, please try again!""")
        readNumber(prompt)
    }
  }
}
