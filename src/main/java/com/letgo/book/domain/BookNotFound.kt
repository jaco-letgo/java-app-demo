package com.letgo.book.domain

class BookNotFound private constructor(message: String) : IndexOutOfBoundsException(message) {
    companion object {
        fun withId(id: BookId): BookNotFound {
            return BookNotFound("Book not found with id " + id.value())
        }
    }
}
