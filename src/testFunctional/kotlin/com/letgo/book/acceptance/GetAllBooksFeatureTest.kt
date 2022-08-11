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
            title = "whatever",
        )

        givenAnExistingBookWith(
            id = "5bca2b51-84aa-4b30-8cc6-087637720444",
            title = "olakease",
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
                                    "title":"olakease",
                                    "isEdited":false
                                },
                                {
                                    "id":"8a1ea4da-e75f-4acd-8432-2adb2adcdf02",
                                    "title":"whatever",
                                    "isEdited":false
                                }
                            ]
                        }
                    """
                ),
                body
            )
        }
    }

    @Test
    fun `It should return paginated books`() {
        givenAnExistingBookWith(
            id = "8a1ea4da-e75f-4acd-8432-2adb2adcdf03",
            title = "1",
        )

        givenAnExistingBookWith(
            id = "8a1ea4da-e75f-4acd-8432-2adb2adcdf04",
            title = "2",
        )

        givenAnExistingBookWith(
            id = "8a1ea4da-e75f-4acd-8432-2adb2adcdf05",
            title = "3",
        )

        givenAnExistingBookWith(
            id = "8a1ea4da-e75f-4acd-8432-2adb2adcdf06",
            title = "4",
        )

        givenAnExistingBookWith(
            id = "8a1ea4da-e75f-4acd-8432-2adb2adcdf02",
            title = "whatever",
        )

        givenAnExistingBookWith(
            id = "5bca2b51-84aa-4b30-8cc6-087637720444",
            title = "olakease",
        )

        get("?elements=2&page=3").run {
            assertSame(HttpStatus.OK, statusCode)
            assertEquals(
                aJsonBodyWith(
                    """
                        {
                            "books": [
                                {
                                    "id":"5bca2b51-84aa-4b30-8cc6-087637720444",
                                    "title":"olakease",
                                    "isEdited":false
                                },
                                {
                                    "id":"8a1ea4da-e75f-4acd-8432-2adb2adcdf02",
                                    "title":"whatever",
                                    "isEdited":false
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
