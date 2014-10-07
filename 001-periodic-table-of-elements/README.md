Example 1: Periodic table of elements
=====================================

In today's lession, we will be modeling a [Periodic table of elements](http://en.wikipedia.org/wiki/Periodic_table).

Introduction
------------

To keep things simple, an element will have only two fields:
 * `number`: Element's atomic nuber (an integer)
 * `name`: Element's english name (a string)

Since `number` uniquely identifies an element (we'll ignore isotopes) it will be element's primary key.


DSL
---
To review, we need an `Element` object with an integer filed named `number` (which is the primary key), and a string field named `name`.

```
module PeriodicTable
{
  aggregate Element(number) {
    Int number;
    String name;
  }
}
```

When compiled, this DSL will generate the database and client code in a selected language. Currently supported are [C#](csharp/README.md), [Java](java/README.md), [Scala](scala/README.md) and [PHP](php/README.md).

Database
--------
Generated database table will look something like this (in PostgreSQL syntax):

```sql
CREATE TABLE "PeriodicTable"."Element"
(
  "number" integer NOT NULL,
  name character varying NOT NULL,
  CONSTRAINT "pk_PeriodicElement" PRIMARY KEY (number)
)
```

Active Record
-------------

The purpose of this example is to demonstrate how [Active Record pattern](http://en.wikipedia.org/wiki/Active_record_pattern "Active Record") is implemented in Platform. It is the simplest way to interact with aggregates, but it is a bit of an antipattern. Some issues with AR:
 * Coupling of database interaction and application logic
 * It can only work with one aggregate at a time.
 * All calls are blocking.

Recommended way to use the Platform is through Repositories. Their usage is explained in [Example 2: World wonders](002-world-wonders.README.md "World wonders example").
