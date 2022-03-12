package com.letgo.book.acceptance

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

private class GetAllBooksFeatureTest : TestCase() {
    @Test
    fun `It should return all existing books`() {

        givenAnExistingBookWith(
            id = "8a1ea4da-e75f-4acd-8432-2adb2adcdf02",
            title = "olakease",
        )

        givenAnExistingBookWith(
            id = "5bca2b51-84aa-4b30-8cc6-087637720444",
            title = "whatever",
        )

        get().run {
            assertSame(HttpStatus.OK, statusCode)
            assertEquals(
                aJsonBodyWith(
                    """
                        {
                            "books": [
                                {
                                    "id":"5bca2b51-84aa-4b30-8cc6-087637720444",
                                    "title":"whatever",
                                    "edited":false
                                },
                                {
                                    "id":"8a1ea4da-e75f-4acd-8432-2adb2adcdf02",
                                    "title":"olakease",
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
