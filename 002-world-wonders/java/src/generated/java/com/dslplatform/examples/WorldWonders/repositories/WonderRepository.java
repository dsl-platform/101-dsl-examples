package com.dslplatform.examples.WorldWonders.repositories;

public class WonderRepository
        extends
        com.dslplatform.client.ClientPersistableRepository<com.dslplatform.examples.WorldWonders.Wonder> {
    public WonderRepository(
            final com.dslplatform.patterns.ServiceLocator locator) {
        super(com.dslplatform.examples.WorldWonders.Wonder.class, locator);
    }
}
