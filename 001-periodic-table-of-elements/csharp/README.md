Example 1: Periodic table of elements - Java
============================================

As explained in [the introduction](../README.md), we will be modelig a periodic table of elements to demonstrate Active Record CRUD:
```
module PeriodicTable
{
  aggregate Element(number)
  {
    Int     number;
    String  name;
  }
}
```

When compiled, this DSL will create the ClientModel assembly which will contain Element class. For this example, only the following methods are interesting:

```java
// Constructors
public Element();

// Getters and Setters
public int number { set; get; }
public string name { set; get; }

// CRUD methods
public PeriodicTable.Element Create();
public static PeriodicTable.Element Find(string uri);
public PeriodicTable.Element Update();
public PeriodicTable.Element Delete();
```


Using Active Record
-------------------

Start with a nice, dependable element and persist it.
```
Element element = new Element();
element.number  = 94;
element.name    = "Plutonium"
element.Create();
```
The first 3 lines initialize an instance of `Element`, and the call to `Create()` persists it.
After inserting, our database might look like this:

![Database after inserting](../plutonium-insert.png?raw=true "Database after inserting")


Check that it really is persisted.
```
Element readElement = Element.Find("94");
Debug.Assert(readElement == element);
```


As *Plutonium* is known to decay, it needs updating.
```
element.number = 92;
element.name   = "Uranium";
element.Update();
```
This will cause a database row previously occupied by *Plutonium* to switch to *Uranium*:

![Database after updating](../uranium-update.png?raw=true "Database after updating")


Check and doublecheck.
```
Element updatedElement = Element.Find("92");
Debug.Assert(updatedElement == element);
```

Finally, get rid of evidence.
```
updatedElement.Delete();
```
This will remove *Uranium* from the database.


Example application
-------------------------

The example application is a simple [REPL](http://en.wikipedia.org/wiki/Read%E2%80%93eval%E2%80%93print_loop "REPL") which demonstrates how to use CRUD on aggregates.

Just load the solution in Visual Studio 2010 or greater (with [Platform Visual Studio Extension](https://visualstudiogallery.msdn.microsoft.com/5b8a140c-5c84-40fc-a551-b255ba7676f4 "DDD for DSL Platofrm") installed), compile, and run it.
