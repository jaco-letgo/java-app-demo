package com.letgo.shared.infrastructure.bus.query

import com.letgo.shared.application.bus.query.Query
import com.letgo.shared.application.bus.query.QueryHandler
import com.letgo.shared.application.bus.query.QueryResponse
import com.letgo.shared.infrastructure.InfrastructureService

@InfrastructureService
class QueryHandlerFinder(handlers: List<QueryHandler<out Query<QueryResponse>, out QueryResponse>>) {
    private val handlers = handlers.associate {
        it.queryClass() to it as QueryHandler<Query<QueryResponse>, QueryResponse>
    }

    fun <Q : Query<R>, R : QueryResponse> forQuery(query: Q): QueryHandler<Q, R> =
        (
            handlers[query::class] ?: throw NoSuchElementException("No handler found for ${query::class}")
            ) as QueryHandler<Q, R>

    private fun QueryHandler<out Query<QueryResponse>, out QueryResponse>.queryClass() = this::class
        .supertypes.firstNotNullOf { supertype ->
            supertype.arguments.firstNotNullOf { argument ->
                argument.type?.classifier?.takeIf {
                    it::class.isInstance(Query::class)
                }
            }
        }
}
