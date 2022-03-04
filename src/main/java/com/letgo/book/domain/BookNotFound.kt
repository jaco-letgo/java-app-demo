package com.letgo.book.domain

class BookNotFound private constructor(message: String) : NoSuchElementException(message) {
    companion object {
        fun with(id: BookId) = BookNotFound("Book not found with id ${id.value()}")
    }
}
