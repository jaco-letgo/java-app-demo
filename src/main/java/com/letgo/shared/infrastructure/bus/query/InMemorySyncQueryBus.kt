package com.letgo.shared.infrastructure.bus.query

import com.letgo.shared.application.bus.query.Query
import com.letgo.shared.application.bus.query.QueryBus
import com.letgo.shared.application.bus.query.QueryHandler
import com.letgo.shared.application.bus.query.QueryResponse
import com.letgo.shared.infrastructure.InfrastructureService
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.function.Consumer

@InfrastructureService
class InMemorySyncQueryBus(handlers: List<QueryHandler<out Query>>) : QueryBus {
    private val handlers: MutableMap<Class<out Query>, QueryHandler<out Query>> = HashMap()

    init {
        handlers.forEach(Consumer { queryHandler: QueryHandler<out Query> ->
            this.handlers[getQueryClass(queryHandler)] = queryHandler
        })
    }

    @Throws(Exception::class)
    override fun dispatch(query: Query): QueryResponse {
        if (!handlers.containsKey(query.javaClass)) {
            throw Exception(String.format("No handler found for %s", query.javaClass.name))
        }
        val queryHandler = handlers[query.javaClass] as QueryHandler<Query>
        return queryHandler.handle(query)
    }

    private fun getQueryClass(handler: QueryHandler<out Query>): Class<out Query> {
        return actualTypeArgument(handler) as Class<out Query>
    }

    private fun actualTypeArgument(handler: QueryHandler<out Query>): Type {
        return (handler.javaClass.genericInterfaces[0] as ParameterizedType).actualTypeArguments[0]
    }
}
