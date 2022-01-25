package com.letgo.book.domain

import com.letgo.shared.domain.DomainService

@DomainService
class BookFinder(
    private val repository: BookRepository
) {
    fun find(id: BookId): Book {
        val book = repository.find(id)
        if (book.isEmpty) {
            throw BookNotFound.withId(id)
        }
        return book.get()
    }
}
