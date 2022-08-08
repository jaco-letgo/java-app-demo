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

        put(endpoint = "/$id/title/whatever").run {
            assertSame(HttpStatus.ACCEPTED, statusCode)
            assertFalse(hasBody())
        }

        weWaitForMessagesToBeProcessed()

        get(endpoint = "/$id").run {
            assertEquals(
                aJsonBodyWith(
                    """
                        {
                            "id":"$id",
                            "title":"whatever",
                            "edited":true
                        }
                    """
                ),
                body
            )
        }
    }

    @Test
    fun `It should return accepted response even when book is not found for given id`() {
        put(endpoint = "/${UUID.randomUUID()}/title/olakease").run {
            weWaitForMessagesToBeProcessed()
            assertSame(HttpStatus.ACCEPTED, statusCode)
            assertFalse(hasBody())
        }
    }
}
