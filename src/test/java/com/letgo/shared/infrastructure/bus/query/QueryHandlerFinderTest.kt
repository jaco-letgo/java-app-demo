package com.letgo.shared.infrastructure.bus.query

import com.letgo.shared.application.bus.query.Query
import com.letgo.shared.application.bus.query.QueryHandler
import com.letgo.shared.application.bus.query.QueryResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

private class QueryHandlerFinderTest {
    @Test
    fun `It should return the correct handler for a given query`() {
        val expectedHandler = BarQueryHandler()
        val finder = QueryHandlerFinder(
            listOf(
                FooQueryHandler(),
                expectedHandler
            )
        )

        assertEquals(expectedHandler, finder.forQuery(BarQuery()))
    }

    @Test
    fun `It should throw an exception when there is no handler for the given Query object`() {
        val query = BarQuery()
        val finder = QueryHandlerFinder(listOf(FooQueryHandler()))

        assertThrows<NoSuchElementException> { finder.forQuery(query) }.run {
            assertEquals("No handler found for ${query::class}", message)
        }
    }

    private class FooQuery : Query
    private class BarQuery : Query
    private class AQueryResponse : QueryResponse

    private class FooQueryHandler : QueryHandler<FooQuery, AQueryResponse> {
        override fun handle(query: FooQuery) = AQueryResponse()
    }

    private class BarQueryHandler : QueryHandler<BarQuery, AQueryResponse> {
        override fun handle(query: BarQuery) = AQueryResponse()
    }
}
