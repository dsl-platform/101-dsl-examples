package com.dslplatform.examples.AggregateCrud.repositories;

public class PlanetRepository
        extends
        com.dslplatform.client.ClientPersistableRepository<com.dslplatform.examples.AggregateCrud.Planet> {
    public PlanetRepository(
            final com.dslplatform.patterns.ServiceLocator locator) {
        super(com.dslplatform.examples.AggregateCrud.Planet.class, locator);
    }
}
