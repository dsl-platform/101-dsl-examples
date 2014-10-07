package com.dslplatform.examples.WorldWonders;

public class Wonder implements java.io.Serializable,
        com.dslplatform.patterns.AggregateRoot {
    public Wonder() {
        URI = java.util.UUID.randomUUID().toString();
        this.englishName = "";
        this.nativeNames = new java.util.ArrayList<String>();
        this.isAncient = false;
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
        final Wonder other = (Wonder) obj;

        return URI.equals(other.URI);
    }

    @Override
    public String toString() {
        return "Wonder(" + URI + ')';
    }

    private static final long serialVersionUID = 0x0097000a;

    @com.fasterxml.jackson.annotation.JsonCreator
    private Wonder(
            @com.fasterxml.jackson.annotation.JacksonInject("_serviceLocator") final com.dslplatform.patterns.ServiceLocator _serviceLocator,
            @com.fasterxml.jackson.annotation.JsonProperty("URI") final String URI,
            @com.fasterxml.jackson.annotation.JsonProperty("englishName") final String englishName,
            @com.fasterxml.jackson.annotation.JsonProperty("nativeNames") final java.util.List<String> nativeNames,
            @com.fasterxml.jackson.annotation.JsonProperty("isAncient") final boolean isAncient) {
        this._serviceLocator = _serviceLocator;
        this.URI = URI != null ? URI : new java.util.UUID(0L, 0L).toString();
        this.englishName = englishName == null ? "" : englishName;
        this.nativeNames = nativeNames == null
                ? new java.util.ArrayList<String>()
                : nativeNames;
        this.isAncient = isAncient;
    }

    public boolean isNewAggregate() {
        return _serviceLocator == null;
    }

    public static Wonder find(final String uri) throws java.io.IOException {
        return find(uri, com.dslplatform.client.Bootstrap.getLocator());
    }

    public static Wonder find(
            final String uri,
            final com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        try {
            return (locator != null
                    ? locator
                    : com.dslplatform.client.Bootstrap.getLocator())
                    .resolve(com.dslplatform.client.CrudProxy.class)
                    .read(Wonder.class, uri).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static java.util.List<Wonder> find(final Iterable<String> uris)
            throws java.io.IOException {
        return find(uris, com.dslplatform.client.Bootstrap.getLocator());
    }

    public static java.util.List<Wonder> find(
            final Iterable<String> uris,
            final com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        try {
            return (locator != null
                    ? locator
                    : com.dslplatform.client.Bootstrap.getLocator())
                    .resolve(com.dslplatform.client.DomainProxy.class)
                    .find(Wonder.class, uris).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static java.util.List<Wonder> search() throws java.io.IOException {
        return search(null, null, com.dslplatform.client.Bootstrap.getLocator());
    }

    public static java.util.List<Wonder> search(
            final com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        return search(null, null, locator);
    }

    public static java.util.List<Wonder> search(
            final Integer limit,
            final Integer offset) throws java.io.IOException {
        return search(limit, offset,
                com.dslplatform.client.Bootstrap.getLocator());
    }

    public static java.util.List<Wonder> search(
            final Integer limit,
            final Integer offset,
            final com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        try {
            return (locator != null
                    ? locator
                    : com.dslplatform.client.Bootstrap.getLocator())
                    .resolve(com.dslplatform.client.DomainProxy.class)
                    .search(Wonder.class, limit, offset, null).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static java.util.List<Wonder> search(
            final com.dslplatform.patterns.Specification<Wonder> specification)
            throws java.io.IOException {
        return search(specification, null, null,
                com.dslplatform.client.Bootstrap.getLocator());
    }

    public static java.util.List<Wonder> search(
            final com.dslplatform.patterns.Specification<Wonder> specification,
            final com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        return search(specification, null, null, locator);
    }

    public static java.util.List<Wonder> search(
            final com.dslplatform.patterns.Specification<Wonder> specification,
            final Integer limit,
            final Integer offset) throws java.io.IOException {
        return search(specification, limit, offset,
                com.dslplatform.client.Bootstrap.getLocator());
    }

    public static java.util.List<Wonder> search(
            final com.dslplatform.patterns.Specification<Wonder> specification,
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
                    .count(Wonder.class).get().longValue();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static long count(
            final com.dslplatform.patterns.Specification<Wonder> specification)
            throws java.io.IOException {
        return count(specification,
                com.dslplatform.client.Bootstrap.getLocator());
    }

    public static long count(
            final com.dslplatform.patterns.Specification<Wonder> specification,
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

    private void updateWithAnother(final Wonder result) {
        this.URI = result.URI;

        this.englishName = result.englishName;
        this.nativeNames = result.nativeNames;
        this.isAncient = result.isAncient;
    }

    public Wonder create() throws java.io.IOException {
        return create(_serviceLocator != null
                ? _serviceLocator
                : com.dslplatform.client.Bootstrap.getLocator());
    }

    public Wonder create(com.dslplatform.patterns.ServiceLocator locator)
            throws java.io.IOException {
        final Wonder result;
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

    public Wonder update() throws java.io.IOException {
        if (_serviceLocator == null)
            throw new java.io.IOException(
                    "Can't update newly created aggregate root");
        final Wonder result;
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

    public Wonder delete() throws java.io.IOException {
        if (_serviceLocator == null)
            throw new java.io.IOException(
                    "Can't delete newly created aggregate root");
        try {
            com.dslplatform.client.CrudProxy proxy = _serviceLocator
                    .resolve(com.dslplatform.client.CrudProxy.class);
            return proxy.delete(Wonder.class, URI).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    private String englishName;

    @com.fasterxml.jackson.annotation.JsonProperty("englishName")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public String getEnglishName() {
        return englishName;
    }

    public Wonder setEnglishName(final String value) {
        if (value == null)
            throw new IllegalArgumentException(
                    "Property \"englishName\" cannot be null!");
        this.englishName = value;

        return this;
    }

    private java.util.List<String> nativeNames;

    @com.fasterxml.jackson.annotation.JsonProperty("nativeNames")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public java.util.List<String> getNativeNames() {
        return nativeNames;
    }

    public Wonder setNativeNames(final java.util.List<String> value) {
        if (value == null)
            throw new IllegalArgumentException(
                    "Property \"nativeNames\" cannot be null!");
        com.dslplatform.examples.Guards.checkNulls(value);
        this.nativeNames = value;

        return this;
    }

    private boolean isAncient;

    @com.fasterxml.jackson.annotation.JsonProperty("isAncient")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public boolean getIsAncient() {
        return isAncient;
    }

    public Wonder setIsAncient(final boolean value) {
        this.isAncient = value;

        return this;
    }

    public Wonder(
            final String englishName,
            final java.util.List<String> nativeNames,
            final boolean isAncient) {
        setEnglishName(englishName);
        setNativeNames(nativeNames);
        setIsAncient(isAncient);
    }

}
