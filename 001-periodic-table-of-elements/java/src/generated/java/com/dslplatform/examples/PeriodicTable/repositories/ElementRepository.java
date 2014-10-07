package com.dslplatform.examples.PeriodicTable.repositories;

public class ElementRepository
        extends
        com.dslplatform.client.ClientPersistableRepository<com.dslplatform.examples.PeriodicTable.Element> {
    public ElementRepository(
            final com.dslplatform.patterns.ServiceLocator locator) {
        super(com.dslplatform.examples.PeriodicTable.Element.class, locator);
    }
}
