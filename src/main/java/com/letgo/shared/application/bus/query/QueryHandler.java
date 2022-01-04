package com.letgo.shared.application.bus.query;

public interface QueryHandler<T extends Query> {
    public QueryResponse handle(T query) throws Exception;
}
