package com.letgo.book.domain

import com.letgo.shared.domain.DomainService

@DomainService
class BookFinder(
    private val repository: BookRepository
) {
    fun find(id: BookId): Book {
        return repository.find(id).let {
            if (!it.isEmpty) it.get() else throw BookNotFound.withId(id)
        }
    }
}
