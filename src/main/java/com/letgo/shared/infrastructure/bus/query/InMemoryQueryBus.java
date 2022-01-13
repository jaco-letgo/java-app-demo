package com.letgo.shared.infrastructure.bus.query;

import com.letgo.shared.application.bus.query.Query;
import com.letgo.shared.application.bus.query.QueryBus;
import com.letgo.shared.application.bus.query.QueryHandler;
import com.letgo.shared.application.bus.query.QueryResponse;
import com.letgo.shared.infrastructure.InfrastructureService;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@InfrastructureService
final public class InMemoryQueryBus implements QueryBus {
    private final Map<Class<? extends Query>, QueryHandler<? extends Query>> handlers = new HashMap<>();

    public InMemoryQueryBus(List<QueryHandler<? extends Query>> handlers) {
        handlers.forEach(queryHandler -> this.handlers.put(getQueryClass(queryHandler), queryHandler));
    }

    @Override
    public QueryResponse dispatch(Query query) throws Exception {
        if (!handlers.containsKey(query.getClass())) {
            throw new Exception(String.format("No handler found for %s", query.getClass().getName()));
        }
        QueryHandler<Query> queryHandler = (QueryHandler<Query>) handlers.get(query.getClass());
        return queryHandler.handle(query);
    }

    private Class<? extends Query> getQueryClass(QueryHandler<? extends Query> handler) {
        return (Class<? extends Query>) actualTypeArgument(handler);
    }

    private Type actualTypeArgument(QueryHandler<? extends Query> handler) {
        return ((ParameterizedType) handler.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }
}
