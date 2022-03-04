package com.letgo.book.domain

import com.letgo.shared.domain.DomainService

@DomainService
class BookFinder(
    private val repository: BookRepository,
) {
    fun find(id: BookId): Book = repository.find(id) ?: throw BookNotFound.with(id)
}
