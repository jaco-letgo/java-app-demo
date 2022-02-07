package com.letgo.book.acceptance

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.*

class CreateBookFeatureTest : TestCase() {
    @Test
    fun `It should create a book`() {
        val id = UUID.randomUUID()
        post("""{"id": $id, "title": "OlaKeAse"}""").run {
            assertSame(HttpStatus.CREATED, this.statusCode)
            assertFalse(this.hasBody())
        }
        weWaitForMessagesToBeProcessed()
        get("/book/$id").run {
            assertEquals("OLAKEASE", this.body)
        }
    }
}
