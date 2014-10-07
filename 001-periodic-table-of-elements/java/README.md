Example 1: Periodic table of elements - Java
============================================

As explained in [the introduction](../README.md), we will be modelig a periodic table of elements to demonstrate Active Record CRUD:
```
module PeriodicTable
{
  aggregate Element(number) {
    Int number;
    String name;
  }
}
```

When compiled, this DSL will create a [Element](src/generated/java/com/dslplatform/examples/AggregateCrud/PeriodicElement.java "generated Element class") class. There's a lot of stuff in there, but for this example, only the following methods are interesting:

```java
// Constructors
public Element()
public Element(final int number, final String name)

// Getters and setters
public int getNumber()
public Element setNumber(final int value)

public String getName()
public Element setName(final String value)

// CRUD methods
public Element create()
public static Element find(final String uri)
public Element update()
public Element delete()
```


Using Active Record
-------------------

Start with a nice, dependable element and persist it.
```java
final Element element = new Element(94, "Plutonium");
element.create();
```
The first line initializes an instance of `Element`, and the call to `create()` persists it.
After inserting, our database might look like this:

![Database after inserting](../plutonium-insert.png?raw=true "Database after inserting")


Check that it really is persisted.
```java
final Element readElement = Element.find("94");
assertEquals(readElement, element);
```


As *Plutonium* is known to decay, it needs updating.
```java
element.setNumber(92);
element.setName("Uranium");
element.update();
```
This will cause a database row previously occupied by *Plutonium* to switch to *Uranium*:

![Database after updating](../uranium-update.png?raw=true "Database after updating")


Check and doublecheck.
```java
final Element updatedElement = Element.find("92");
assertEquals(updatedElement, element);
```

Finally, get rid of evidence.
```java
updatedElement.delete();
```
This will remove *Uranium* from the database.
