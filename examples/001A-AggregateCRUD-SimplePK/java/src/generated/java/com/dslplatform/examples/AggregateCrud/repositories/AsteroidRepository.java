package com.dslplatform.examples.AggregateCrud.repositories;

public class AsteroidRepository
        extends
        com.dslplatform.client.ClientPersistableRepository<com.dslplatform.examples.AggregateCrud.Asteroid> {
    public AsteroidRepository(
            final com.dslplatform.patterns.ServiceLocator locator) {
        super(com.dslplatform.examples.AggregateCrud.Asteroid.class, locator);
    }
}
