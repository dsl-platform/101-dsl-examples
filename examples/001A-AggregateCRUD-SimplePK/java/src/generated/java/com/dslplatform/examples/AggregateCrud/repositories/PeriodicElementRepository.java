package com.dslplatform.examples.AggregateCrud.repositories;

public class PeriodicElementRepository
        extends
        com.dslplatform.client.ClientPersistableRepository<com.dslplatform.examples.AggregateCrud.PeriodicElement> {
    public PeriodicElementRepository(
            final com.dslplatform.patterns.ServiceLocator locator) {
        super(com.dslplatform.examples.AggregateCrud.PeriodicElement.class,
                locator);
    }
}
