package com.dslplatform.examples.AggregateCrud;

public class Asteroid implements java.io.Serializable,
        com.dslplatform.patterns.AggregateRoot {
    public Asteroid() {
        URI = java.util.UUID.randomUUID().toString();
        this.name = "";
    }

    private transient com.dslplatform.patterns.ServiceLocator _serviceLocator;

    private String URI;

    @com.fasterxml.jackson.annotation.JsonProperty("URI")
    public String getURI() {
        return this.URI;
    }

    @Override
    public int hashCode() {
        return URI.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;

        if (getClass() != obj.getClass()) return false;
        final Asteroid other = (Asteroid) obj;

        return URI.equals(other.URI);
    }

    @Override
    public String toString() {
        return "Asteroid(" + URI + ')';
    }

    private static final long serialVersionUID = 0x0097000a;

    @com.fasterxml.jackson.annotation.JsonCreator
    private Asteroid(
            @com.fasterxml.jackson.annotation.JacksonInject("_serviceLocator") final com.dslplatform.patterns.ServiceLocator _serviceLocator,
            @com.fasterxml.jackson.annotation.JsonProperty("URI") final String URI,
            @com.fasterxml.jackson.annotation.JsonProperty("name") final String name) {
        this._serviceLocator = _serviceLocator;
        this.URI = URI != null ? URI : new java.util.UUID(0L, 0L).toString();
        this.name = name == null ? "" : name;
    }

    public boolean isNewAggregate() {
        return _serviceLocator == null;
    }

    public static Asteroid find(final String uri) throws java.io.IOException {
        return find(uri, com.dslplatform.client.Bootstrap.getLocator());
    }

    public static Asteroid find(
            final String uri,
            final com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        try {
            return (locator != null
                    ? locator
                    : com.dslplatform.client.Bootstrap.getLocator())
                    .resolve(com.dslplatform.client.CrudProxy.class)
                    .read(Asteroid.class, uri).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static java.util.List<Asteroid> find(final Iterable<String> uris)
            throws java.io.IOException {
        return find(uris, com.dslplatform.client.Bootstrap.getLocator());
    }

    public static java.util.List<Asteroid> find(
            final Iterable<String> uris,
            final com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        try {
            return (locator != null
                    ? locator
                    : com.dslplatform.client.Bootstrap.getLocator())
                    .resolve(com.dslplatform.client.DomainProxy.class)
                    .find(Asteroid.class, uris).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static java.util.List<Asteroid> search() throws java.io.IOException {
        return search(null, null, com.dslplatform.client.Bootstrap.getLocator());
    }

    public static java.util.List<Asteroid> search(
            final com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        return search(null, null, locator);
    }

    public static java.util.List<Asteroid> search(
            final Integer limit,
            final Integer offset) throws java.io.IOException {
        return search(limit, offset,
                com.dslplatform.client.Bootstrap.getLocator());
    }

    public static java.util.List<Asteroid> search(
            final Integer limit,
            final Integer offset,
            final com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        try {
            return (locator != null
                    ? locator
                    : com.dslplatform.client.Bootstrap.getLocator())
                    .resolve(com.dslplatform.client.DomainProxy.class)
                    .search(Asteroid.class, limit, offset, null).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static java.util.List<Asteroid> search(
            final com.dslplatform.patterns.Specification<Asteroid> specification)
            throws java.io.IOException {
        return search(specification, null, null,
                com.dslplatform.client.Bootstrap.getLocator());
    }

    public static java.util.List<Asteroid> search(
            final com.dslplatform.patterns.Specification<Asteroid> specification,
            final com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        return search(specification, null, null, locator);
    }

    public static java.util.List<Asteroid> search(
            final com.dslplatform.patterns.Specification<Asteroid> specification,
            final Integer limit,
            final Integer offset) throws java.io.IOException {
        return search(specification, limit, offset,
                com.dslplatform.client.Bootstrap.getLocator());
    }

    public static java.util.List<Asteroid> search(
            final com.dslplatform.patterns.Specification<Asteroid> specification,
            final Integer limit,
            final Integer offset,
            final com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        try {
            return (locator != null
                    ? locator
                    : com.dslplatform.client.Bootstrap.getLocator())
                    .resolve(com.dslplatform.client.DomainProxy.class)
                    .search(specification, limit, offset, null).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static long count() throws java.io.IOException {
        return count(com.dslplatform.client.Bootstrap.getLocator());
    }

    public static long count(
            final com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        try {
            return (locator != null
                    ? locator
                    : com.dslplatform.client.Bootstrap.getLocator())
                    .resolve(com.dslplatform.client.DomainProxy.class)
                    .count(Asteroid.class).get().longValue();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static long count(
            final com.dslplatform.patterns.Specification<Asteroid> specification)
            throws java.io.IOException {
        return count(specification,
                com.dslplatform.client.Bootstrap.getLocator());
    }

    public static long count(
            final com.dslplatform.patterns.Specification<Asteroid> specification,
            final com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        try {
            return (locator != null
                    ? locator
                    : com.dslplatform.client.Bootstrap.getLocator())
                    .resolve(com.dslplatform.client.DomainProxy.class)
                    .count(specification).get().longValue();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    private void updateWithAnother(final Asteroid result) {
        this.URI = result.URI;

        this.name = result.name;
    }

    public Asteroid create() throws java.io.IOException {
        return create(_serviceLocator != null
                ? _serviceLocator
                : com.dslplatform.client.Bootstrap.getLocator());
    }

    public Asteroid create(com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        final Asteroid result;
        try {
            com.dslplatform.client.CrudProxy proxy = (locator != null
                    ? locator
                    : com.dslplatform.client.Bootstrap.getLocator())
                    .resolve(com.dslplatform.client.CrudProxy.class);
            result = proxy.create(this).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
        this.updateWithAnother(result);
        this._serviceLocator = locator != null
                ? locator
                : com.dslplatform.client.Bootstrap.getLocator();
        return this;
    }

    public Asteroid update() throws java.io.IOException {
        if (_serviceLocator == null)
            throw new java.io.IOException(
                    "Can't update newly created aggregate root");
        final Asteroid result;
        try {
            com.dslplatform.client.CrudProxy proxy = _serviceLocator
                    .resolve(com.dslplatform.client.CrudProxy.class);
            result = proxy.update(this).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
        this.updateWithAnother(result);
        return this;
    }

    public Asteroid delete() throws java.io.IOException {
        if (_serviceLocator == null)
            throw new java.io.IOException(
                    "Can't delete newly created aggregate root");
        try {
            com.dslplatform.client.CrudProxy proxy = _serviceLocator
                    .resolve(com.dslplatform.client.CrudProxy.class);
            return proxy.delete(Asteroid.class, URI).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    private String name;

    @com.fasterxml.jackson.annotation.JsonProperty("name")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public String getName() {
        return name;
    }

    public Asteroid setName(final String value) {
        if (value == null)
            throw new IllegalArgumentException(
                    "Property \"name\" cannot be null!");
        this.name = value;

        return this;
    }

    public Asteroid(
            final String name) {
        setName(name);
    }

}
