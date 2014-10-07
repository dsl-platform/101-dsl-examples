package com.dslplatform.examples;

import com.dslplatform.client.Bootstrap;
import com.dslplatform.patterns.ServiceLocator;
import java.io.IOException;
import org.slf4j.Logger;

public class Model {
    /**
     * Call to initialize an instance of ServiceLocator with a dsl-project.props
     */
    public static ServiceLocator init() throws IOException {
        final ServiceLocator locator = Bootstrap.init(Model.class.getResourceAsStream("/dsl-project.props"));

        // an example how to resolve components from the ServiceLocator
        locator.resolve(Logger.class).info("Locator has been initialized.");
        return locator;
    }

    /**
     * ExecutorService will keep on working up to a minute after program has
     * finished. This method is an example how one could quickly exit the
     * program and can be called to speed up application ending.
     *
     * Alternatively, you can also invoke System.exit();
     */
    public static void shutdown(final ServiceLocator locator) throws IOException {
        locator.resolve(java.util.concurrent.ExecutorService.class).shutdown();
    }
}
