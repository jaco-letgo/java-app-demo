package com.letgo.book.acceptance

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.UUID

private class ChangeTitleFeatureTest : TestCase() {
    @Test
    fun `It should change a book title`() {
        val id = UUID.randomUUID()

        givenAnExistingBookWith(
            id = id.toString(),
            title = "OlaKeAse"
        )

        put(endpoint = "/book/$id/title/whatever").run {
            assertSame(HttpStatus.ACCEPTED, statusCode)
            assertFalse(hasBody())
        }

        weWaitForMessagesToBeProcessed()

        get(endpoint = "/book/$id").run {
            assertEquals("whatever", body)
        }
    }

    @Test
    fun `It should return accepted response even when book is not found for given id`() {
        put(endpoint = "/book/${UUID.randomUUID()}/title/olakease").run {
            weWaitForMessagesToBeProcessed()
            assertSame(HttpStatus.ACCEPTED, statusCode)
            assertFalse(hasBody())
        }
    }
}
