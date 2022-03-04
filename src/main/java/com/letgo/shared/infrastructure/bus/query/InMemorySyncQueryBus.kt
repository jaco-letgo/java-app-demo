package com.letgo.shared.infrastructure.bus.query

import com.letgo.shared.application.bus.query.Query
import com.letgo.shared.application.bus.query.QueryBus
import com.letgo.shared.application.bus.query.QueryHandler
import com.letgo.shared.application.bus.query.QueryResponse
import com.letgo.shared.infrastructure.InfrastructureService
import kotlin.reflect.KClass

@InfrastructureService
class InMemorySyncQueryBus(handlers: List<QueryHandler<out Query, out QueryResponse>>) : QueryBus {
    private val handlers: Map<KClass<out Query>, QueryHandler<Query, QueryResponse>> =
        handlers.associate {
            getQueryClass(it) to it as QueryHandler<Query, QueryResponse>
        }

    override fun dispatch(query: Query): QueryResponse {
        return handlers[query::class]?.handle(query) ?: throw Exception("No handler found for ${query::class}")
    }

    private fun getQueryClass(queryHandler: QueryHandler<out Query, out QueryResponse>) =
        queryHandler::class
            .supertypes.find { superClass -> superClass.classifier!!::class.isInstance(QueryHandler::class) }!!
            .arguments.find { argument -> argument.type!!.classifier!!::class.isInstance(Query::class) }!!
            .type!!
            .classifier as KClass<out Query>
}
