package com.letgo.shared.application.bus.query;

public interface QueryBus {
    public QueryResponse dispatch(Query query) throws Exception;
}
