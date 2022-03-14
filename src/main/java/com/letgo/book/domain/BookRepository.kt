package com.letgo.book.domain

import com.letgo.shared.domain.criteria.Criteria

interface BookRepository {
    fun all(): List<Book>
    fun find(id: BookId): Book?
    fun findBy(criteria: Criteria): List<Book>
    fun save(book: Book)
}
