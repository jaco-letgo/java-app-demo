package com.letgo.shared.infrastructure.bus.query

import com.letgo.shared.application.bus.query.Query
import com.letgo.shared.application.bus.query.QueryHandler
import com.letgo.shared.application.bus.query.QueryResponse
import com.letgo.shared.infrastructure.InfrastructureService
import kotlin.reflect.KClass

@InfrastructureService
class QueryHandlerFinder(handlers: List<QueryHandler<out Query, out QueryResponse>>) {
    private val handlers =
        handlers.associate { getQueryClass(it) to it as QueryHandler<Query, QueryResponse> }

    fun forQuery(query: Query): QueryHandler<Query, QueryResponse> =
        handlers[query::class] ?: throw NoSuchElementException("No handler found for ${query::class}")

    private fun getQueryClass(queryHandler: QueryHandler<out Query, out QueryResponse>) =
        queryHandler::class
            .supertypes.find { superClass -> superClass.classifier!!::class.isInstance(QueryHandler::class) }!!
            .arguments.find { argument -> argument.type!!.classifier!!::class.isInstance(Query::class) }!!
            .type!!
            .classifier as KClass<out Query>
}
