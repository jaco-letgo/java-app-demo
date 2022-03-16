package com.letgo.book.acceptance

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.UUID

private class FindBooksContainingTitleFeatureTest : TestCase() {
    @Test
    fun `It should find all existing books containing title`() {
        val id1 = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        val id3 = UUID.randomUUID()
        val id4 = UUID.randomUUID()

        givenAnExistingBookWith(
            id = id1.toString(),
            title = "aaaaaa",
        )
        givenAnExistingBookWith(
            id = id2.toString(),
            title = "AAAAAA",
        )
        givenAnExistingBookWith(
            id = id3.toString(),
            title = "bbbbbb",
        )
        givenAnExistingBookWith(
            id = id4.toString(),
            title = "cccccacccc",
        )

        get(endpoint = "/containing/title/a").run {
            assertSame(HttpStatus.OK, statusCode)
            assertEquals(
                aJsonBodyWith(
                    """
                        {
                            "books": [
                                {
                                    "id":"$id1",
                                    "title":"aaaaaa",
                                    "edited":false
                                },
                                {
                                    "id":"$id4",
                                    "title":"cccccacccc",
                                    "edited":false
                                }
                            ]
                        }
                    """
                ),
                body
            )
        }
    }
}
