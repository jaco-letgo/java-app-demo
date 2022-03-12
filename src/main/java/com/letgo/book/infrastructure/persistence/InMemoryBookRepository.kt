package com.letgo.book.infrastructure.persistence

import com.letgo.book.domain.Book
import com.letgo.book.domain.BookId
import com.letgo.book.domain.BookRepository

class InMemoryBookRepository : BookRepository {
    private val storage: MutableMap<BookId, Book> = mutableMapOf()
    override fun all(): List<Book> = storage.values.toList()

    override fun find(id: BookId): Book? = storage[id]

    override fun save(book: Book) {
        storage[book.id()] = book
    }
}
