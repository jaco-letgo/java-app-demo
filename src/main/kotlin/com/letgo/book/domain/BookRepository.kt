package com.letgo.book.domain

import com.letgo.shared.domain.criteria.Criteria

interface BookRepository {
    fun find(id: BookId): Book?
    fun findBy(criteria: Criteria): List<Book>
    fun save(book: Book)
}
