package com.letgo.book.acceptance

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.*

private class ChangeTitleFeatureTest : TestCase() {
    @Test
    fun `It should change a book title`() {
        val id = UUID.randomUUID()
        givenAnExistingBookWith(id.toString(), "OlaKeAse")
        put("/book/$id/title/whatever").run {
            assertSame(HttpStatus.ACCEPTED, statusCode)
            assertFalse(hasBody())
        }
        weWaitForMessagesToBeProcessed()
        get("/book/$id").run {
            assertEquals("whatever", body)
        }
    }

    @Test
    fun `It should return accepted response even when book is not found for given id`() {
        put("/book/${UUID.randomUUID()}/title/olakease").run {
            weWaitForMessagesToBeProcessed()
            assertSame(HttpStatus.ACCEPTED, statusCode)
            assertFalse(hasBody())
        }
    }
}
