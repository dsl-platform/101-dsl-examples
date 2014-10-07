Active Record CRUD
==================

[Active Record pattern](http://en.wikipedia.org/wiki/Active_record_pattern "Active Record"), while being the simplest way to interact with aggregates in DSL Platform, is a bit of an antipattern:
* Coupling of database interaction and application logic
* It can only work with one aggregate at a time.
* All calls are blocking.

Recommended way to inteface with the Platform is through Repositories. Their usage is explained in [Example 002: Repositories]( "Repositories example").

DSL
---
To demonstrate Active Record API, let's start with a simple DSL:

```
module ActiveRecordCrud
{
  aggregate PeriodicElement(number) {
    Int number;
    String name;
  }
}
```

Here, we have a really simple model of the Periodic Table. Each element has a `number` (which is its unique identifier), and a `name`.

When compiled, this DSL will generate the database and client code in a selected language. Currently supported are [C#](csharp/README.md), [Java](java/README.md), [Scala](scala/README.md) and [PHP](php/README.md).


Database
--------
Generated database table will look something like this (in PostgreSQL syntax):

```sql
CREATE TABLE "ActiveRecordCrud"."PeriodicElement"
(
  "number" integer NOT NULL,
  "name" string,
  CONSTRAINT "pk_PeriodicElement" PRIMARY KEY (number)
)
```
