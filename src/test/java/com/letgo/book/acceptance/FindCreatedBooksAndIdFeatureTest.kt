package com.letgo.book.acceptance

import com.letgo.book.domain.BookStatus
import com.letgo.book.unit.domain.ABook
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.UUID

private class FindCreatedBooksAndIdFeatureTest : TestCase() {
    @Test
    fun `It should find all created books and the one with given id`() {
        val id1 = "351e8764-4268-411c-83b2-4f6bdf3c59c6"
        val id2 = "e830aa3d-845c-470f-aaae-45cf21ce91e3"
        val id3 = UUID.randomUUID()
        val id4 = "f2f0bc51-f114-4894-8479-c713efa07e01"

        givenAnExistingBookWith(
            id = id1,
            title = "AAAAAA",
        )
        givenAnExistingBookWith(
            id = id2,
            title = "aaaaab",
        )
        ABook.with(id = id3.toString(), title = "bbbbbb").copy(status = BookStatus.Edited).also {
            bookRepository.save(it)
        }
        givenAnExistingBookWith(
            id = id4,
            title = "cccccacccc",
        )

        get(endpoint = "/with/id/$id1").run {
            assertSame(HttpStatus.OK, statusCode)
            assertEquals(
                aJsonBodyWith(
                    """
                        {
                            "books": [
                                {
                                    "id":"$id1",
                                    "title":"AAAAAA",
                                    "edited":false
                                },
                                {
                                    "id":"$id2",
                                    "title":"aaaaab",
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
