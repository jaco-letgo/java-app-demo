package com.letgo.shared.infrastructure.bus.query

import com.letgo.shared.application.bus.query.Query
import com.letgo.shared.application.bus.query.QueryBus
import com.letgo.shared.application.bus.query.QueryResponse
import com.letgo.shared.infrastructure.InfrastructureService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@InfrastructureService
class InMemoryAsyncQueryBus(
    private val handlerFinder: QueryHandlerFinder,
) : QueryBus {
    override fun dispatch(query: Query): QueryResponse = runBlocking {
        handleAsync(query)
    }

    private suspend fun handleAsync(query: Query): QueryResponse = coroutineScope {
        withContext(Dispatchers.Default) {
            handlerFinder.forQuery(query).handle(query)
        }
    }
}
