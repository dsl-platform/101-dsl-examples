Example 1: Periodic table of elements
=====================================

In today's lession, we will be modeling a [Periodic table of elements](http://en.wikipedia.org/wiki/Periodic_table).

The goal for this project will be to create an application in which you can lookup existing elements,  
and insert a new element whenever you discover a new one - let's asume this happens on a regular basis :)

Introduction
------------

To keep things simple, an element will have only two fields:
 * `number`: Element's atomic nuber (an integer)
 * `name`: Element's English name (a string)

Since `number` uniquely identifies an element (we'll ignore isotopes) it will be element's primary key.


DSL
---

To summarize, we need an `Element` object with an integer field named `number` (which will be the primary key), and a string field called `name`. The module's name will be `PeriodicTable` to reflect the nature of which type of `Element` we're modelling.

```
module PeriodicTable
{
  aggregate Element(number) {
    Int     number;
    String  name;
  }
}
```

When compiled, this DSL will generate the database and client code in a selected language.  
Currently supported are [C#](csharp/README.md), [Java](java/README.md), [Scala](scala/README.md) and [PHP](php/README.md).

Database
--------
Generated database table will look something like this (in PostgreSQL syntax):

```sql
CREATE TABLE "PeriodicTable"."Element"
(
  "number" integer NOT NULL,
  "name" character varying NOT NULL,
  CONSTRAINT "pk_PeriodicElement" PRIMARY KEY ("number")
)
```

Active Record
-------------

The purpose of this example is to demonstrate usage of DSL Platform generated data transfer objects through the [Active Record pattern](http://en.wikipedia.org/wiki/Active_record_pattern "Active Record"). It is the simplest way to interact with aggregates, but it is a bit of an antipattern. Here are some issues with AR:
 * Coupling of database interaction and application logic
 * It can only work with one aggregate at a time.

Explanation:

If an object has a `.persist()` method, it becomes much more difficult to replace persistence logic with,
say, a mock or a caching implementation. The recommended way to do persistence using DSL Platform is
through Repositories. Their usage is explained in [Example 2: World wonders](../002-world-wonders
"World wonders example").
