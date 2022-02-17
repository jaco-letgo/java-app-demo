package com.letgo.book.domain

interface BookRepository {
    fun find(id: BookId): Book?
    fun save(book: Book)
}
