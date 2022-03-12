package com.letgo.book.domain

interface BookRepository {
    fun all(): List<Book>
    fun find(id: BookId): Book?
    fun save(book: Book)
}
