package com.letgo.shared.infrastructure.bus.query

import com.letgo.shared.application.bus.query.Query
import com.letgo.shared.application.bus.query.QueryBus
import com.letgo.shared.application.bus.query.QueryHandler
import com.letgo.shared.application.bus.query.QueryResponse
import com.letgo.shared.infrastructure.InfrastructureService
import java.util.function.Consumer
import kotlin.reflect.KClass

@InfrastructureService
class InMemorySyncQueryBus(handlers: List<QueryHandler<out Query>>) : QueryBus {
    private val handlers: MutableMap<KClass<out Query>, QueryHandler<Query>> = mutableMapOf()

    init {
        handlers.forEach(Consumer { queryHandler: QueryHandler<out Query> ->
            this.handlers[getQueryClass(queryHandler)] = queryHandler as QueryHandler<Query>
        })
    }

    @Throws(Exception::class)
    override fun dispatch(query: Query): QueryResponse {
        synchronized(this) {
            return handlers[query::class]?.handle(query) ?: throw Exception("No handler found for ${query::class}")
        }
    }

    private fun getQueryClass(queryHandler: QueryHandler<out Query>) =
        queryHandler::class
            .supertypes.find { superClass -> superClass.classifier!!::class.isInstance(QueryHandler::class) }!!
            .arguments.find { argument -> argument.type!!.classifier!!::class.isInstance(Query::class) }!!
            .type!!
            .classifier as KClass<out Query>
}
