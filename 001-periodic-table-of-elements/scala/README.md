Example 1: Periodic table of elements - Scala
=============================================

As explained in [the introduction](../README.md), we will be modeling a periodic table of elements to demonstrate Active Record CRUD:
```
module PeriodicTable
{
  aggregate Element(number) {
    Int     number;
    String  name;
  }
}
```

When compiled, this DSL will create the [Element](src/generated/scala/com/dslplatform/examples/PeriodicTable/Element.scala "generated Element class") class. There's a lot of stuff in there, but for this example, only the following methods are interesting:

```java
// Getters and Setters
def number: Int
def number_= : Unit

def name: String
def name_= : Unit

// CRUD methods
def create()(implicit locator: ServiceLocator, ec: ExecutionContext, duration: Duration): Element
def update()(implicit ec: ExecutionContext, duration: Duration): Element
def delete()(implicit ec: ExecutionContext, duration: Duration): Element
```

Element's companion object contains the constructor and `find` and `findAll` methods (inhereded from `com.dslplatform.api.client.AggregateRootCompanion[Element]`):
```
def apply(number: Int = 0, name: String = "")
def findAll(implicit locator: ServiceLocator, duration: Duration): IndexedSeq[Element]
def find(uri: String)(implicit locator: ServiceLocator, duration: Duration): Element
```

Implicits for CRUD methods are defined in [the package object](src/main/scala/com/dslplatform/examples/package.scala "Package object")

Using Active Record
-------------------

Start with a nice, dependable element and persist it.
```
val element = Element(94, "Plutonium")
element.create()
```
The first line initializes an instance of `Element`, and the call to `create()` persists it.
After inserting, our database might look like this:

![Database after inserting](../plutonium-insert.png?raw=true "Database after inserting")


Check that it really is persisted.
```
val readElement = Element.find("94")
assertEquals(readElement, element)
```


As *Plutonium* is known to decay, it needs updating.
```
element.number = 92
element.name = "Uranium"
element.update()
```
This will cause a database row previously occupied by *Plutonium* to switch to *Uranium*:

![Database after updating](../uranium-update.png?raw=true "Database after updating")


Check and doublecheck.
```
val updatedElement = Element.find("92")
assertEquals(updatedElement, element)
```

Finally, get rid of evidence.
```
updatedElement.delete()
```
This will remove *Uranium* from the database.


Example application
-------------------------

The example application is a simple [REPL](http://en.wikipedia.org/wiki/Read%E2%80%93eval%E2%80%93print_loop "REPL") which demonstrates how to use CRUD on aggregates.

Easiest way to run the application is using [SBT](http://maven.apache.org/ "Simple Build Tool").
To run, execute:
```
sbt run
```

If you are using Eclipse, you can run:
```
sbt eclipse
```
This will create an Eclipse project which you can import.
