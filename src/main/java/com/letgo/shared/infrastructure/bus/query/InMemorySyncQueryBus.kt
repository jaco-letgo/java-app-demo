package com.letgo.shared.infrastructure.bus.query

import com.letgo.shared.application.bus.query.Query
import com.letgo.shared.application.bus.query.QueryBus
import com.letgo.shared.application.bus.query.QueryResponse

class InMemorySyncQueryBus(
    private val handlerFinder: QueryHandlerFinder,
) : QueryBus {
    override fun dispatch(query: Query): QueryResponse = handlerFinder.forQuery(query).handle(query)
}
