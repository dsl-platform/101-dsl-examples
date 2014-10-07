101 DSL examples
================

Succinct examples of [DSLPlatform](https://dsl-platform.com/) modeling, bundled with accompanying unit tests.  
Each example directory hosts a solution to a unique everyday line-of-business problem.

The purpose of these examples is to allow for a hands-on approach to DSL Platform.  
Each new example will introduce new features and best practices from the world of Domain-Driven Design.  
Even though the examples are meant to be read in sequence, you can probably jump around if you're interested in a specific topic.



## Gentle introduction to aggregate roots (simple primary key, CRUD)

This section will cover problems which can be modeled using a single aggregate,
and show interactions with domain objects through repository and active record patterns.


### 1. [Periodic table of elements](https://github.com/dsl-platform/101-dsl-examples/tree/master/001-periodic-table-of-elements)
  - aggregates are identified with an Integer as a primary key
  - showcase of CRUD operations via active record pattern

### 2. [World Wonders](https://github.com/dsl-platform/101-dsl-examples/tree/master/002-world-wonders)
  - aggregates are identified with a String primary key of undefined length
  - introduction to Domain-Driven Design repository pattern

### 3. [Second Life Assets](https://github.com/dsl-platform/101-dsl-examples/tree/master/003-second-life-assets)
  - a mock of how UUIDs/GUIDs are used to identify assets in the Secord Life virtual reality game
  - introduction to enums (1st class citizens in the database)
  - introduction to batch processing - CRUD on collections

  
  
## Aggregate roots, continued (managed primary key, CRUD)

This sections shows how you can use database to generate primary keys.  
In case of integers and longs this will use a simple database sequence.  

  
### 4. Surrogate key: Integer
  
### 5. Surrogate key: Long

### 6. Surrogate key: UUID



## Aggregate roots with complex primary key

This section covers some specific ways in which you can uniquely identify your aggregates,
such as through one of their value object properties, through a composite of multiple 
properties. Some constrained and calculated primary keys will also be showcased. 


### 7. ISO Countries
  - constrained primary key example

### 8. Composite key

### 9. Value object key example

### 10. Calculated key example
