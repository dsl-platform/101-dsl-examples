Active Record CRUD - Java
=========================

As explained in [the introduction](../README.md), we will be using the following DSL to demonstrate Active Record CRUD:
```
module ActiveRecordCrud
{
  aggregate PeriodicElement(number) {
    Int number;
    String name;
  }
}
```

When compiled with `-target=java_client` and `-settings=active-record` flags, this DSL will create a `PeriodicElement` class which will contain CRUD methods:

```java
public static PeriodicElement find(final String uri);
public PeriodicElement create();
public PeriodicElement update();
public PeriodicElement delete();

public int getNumber();
public PeriodicElement setNumber(final int value);
```

Using Active Record
-------------------

Start with a nice, dependable element and persist it.
```java
final PeriodicElement element = new PeriodicElement(94, "Plutonium");
element.create();
```
The first line initializes an instance of `PeriodicElement`, and the call to `create()` persists it.
After inserting, our database might look like this:

![Database after inserting](001A-Plutonium-Insert.png?raw=true "Database after inserting")


Check that it really is persisted.
```java
final PeriodicElement readElement = PeriodicElement.find("94");
assertEquals(readElement, element);
```


As *Plutonium* is known to decay, it needs updating.
```java
element.setNumber(92);
element.setName("Uranium");
element.update();
```
This will cause a database row previously occupied by *Plutonium* to switch to *Uranium*:

![Database after updating](001A-Uranium-Update.png?raw=true "Database after updating")


Check and doublecheck.
```java
final PeriodicElement updatedElement = PeriodicElement.find("92");
assertEquals(updatedElement, element);
```

Finally, get rid of evidence.
```java
updatedElement.delete();
```
This will remove *Uranium* from the database.
