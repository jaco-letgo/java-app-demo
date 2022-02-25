package com.letgo.book.acceptance

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.*

private class FindBookFeatureTest : TestCase() {
    @Test
    fun `It should find an existing book`() {
        val id = UUID.randomUUID()
        givenAnExistingBookWith(id.toString(), "whatever")
        get("/book/$id").run {
            assertSame(HttpStatus.OK, statusCode)
            assertEquals("whatever", body)
        }
    }

    @Test
    fun `It should return an error when book is not found for given id`() {
        get("/book/" + UUID.randomUUID()).run {
            assertSame(HttpStatus.NOT_FOUND, statusCode)
            assertFalse(hasBody())
        }
    }
}
