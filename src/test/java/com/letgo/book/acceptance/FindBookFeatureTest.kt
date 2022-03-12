package com.letgo.book.acceptance

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.UUID

private class FindBookFeatureTest : TestCase() {
    @Test
    fun `It should find an existing book`() {
        val id = UUID.randomUUID()

        givenAnExistingBookWith(
            id = id.toString(),
            title = "whatever",
        )

        get(endpoint = "/$id").run {
            assertSame(HttpStatus.OK, statusCode)
            assertEquals(
                aJsonBodyWith(
                    """
                        {
                            "id":"$id",
                            "title":"whatever",
                            "edited":false
                        }
                    """
                ),
                body
            )
        }
    }

    @Test
    fun `It should return an error when book is not found for given id`() {
        get(endpoint = "/${UUID.randomUUID()}").run {
            assertSame(HttpStatus.NOT_FOUND, statusCode)
            assertFalse(hasBody())
        }
    }
}
