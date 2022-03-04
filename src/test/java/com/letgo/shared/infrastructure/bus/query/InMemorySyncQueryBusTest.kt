package com.letgo.shared.infrastructure.bus.query

import com.letgo.shared.application.bus.query.Query
import com.letgo.shared.application.bus.query.QueryHandler
import com.letgo.shared.application.bus.query.QueryResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

private class InMemorySyncQueryBusTest {
    @Test
    fun `It should dispatch Query objects`() {
        val query = BarQuery("olakease")
        val queryBus = InMemorySyncQueryBus(
            listOf(
                FooQueryHandler(),
                BarQueryHandler(),
            )
        )

        val response = queryBus.dispatch(query) as AQueryResponse

        assertEquals(query, response.query)
        assertTrue(response is BarQueryResponse)
    }

    @Test
    fun `It should throw an exception when there is no handler for the given Query object`() {
        val query = BarQuery("olakease")
        val queryBus = InMemorySyncQueryBus(
            listOf(
                FooQueryHandler(),
            )
        )

        assertThrows<Exception> { queryBus.dispatch(query) }.run {
            assertEquals("No handler found for ${query::class}", message)
        }
    }

    private data class FooQuery(private val property: String) : Query
    private data class BarQuery(private val property: String) : Query
    private interface AQueryResponse : QueryResponse { val query: Query }
    private data class FooQueryResponse(override val query: Query) : AQueryResponse
    private data class BarQueryResponse(override val query: Query) : AQueryResponse

    private class FooQueryHandler : QueryHandler<FooQuery, AQueryResponse> {
        override fun handle(query: FooQuery): AQueryResponse = FooQueryResponse(query)
    }

    private class BarQueryHandler : QueryHandler<BarQuery, AQueryResponse> {
        override fun handle(query: BarQuery): AQueryResponse = BarQueryResponse(query)
    }
}
