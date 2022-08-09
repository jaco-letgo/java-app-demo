package com.letgo.shared.infrastructure.bus.query

import com.letgo.shared.application.bus.query.Query
import com.letgo.shared.application.bus.query.QueryHandler
import com.letgo.shared.application.bus.query.QueryResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private class InMemoryAsyncQueryBusTest {
    @Test
    fun `It should dispatch a query and return its response`() {
        val query = AQuery("olakease")
        val queryBus = InMemoryAsyncQueryBus(QueryHandlerFinder(listOf(AQueryHandler())))

        assertEquals(AQueryResponse(query.property), queryBus.dispatch(query))
    }

    private data class AQuery(val property: String) : Query<AQueryResponse>
    private data class AQueryResponse(val property: String) : QueryResponse

    private class AQueryHandler : QueryHandler<AQuery, AQueryResponse> {
        override fun handle(query: AQuery): AQueryResponse = AQueryResponse(query.property)
    }
}
