package com.letgo.book.acceptance

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.UUID

private class CreateBookFeatureTest : TestCase() {
    @Test
    fun `It should create a book`() {
        val id = UUID.randomUUID()

        post(
            body = """
                {
                    "id": "$id",
                    "title": "OlaKeAse"
                }
            """.trimIndent()
        ).run {
            assertSame(HttpStatus.CREATED, statusCode)
            assertFalse(hasBody())
        }

        weWaitForMessagesToBeProcessed()

        get(endpoint = "/book/$id").run {
            assertEquals("OLAKEASE", body)
        }
    }
}
