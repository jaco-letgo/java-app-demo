package com.letgo.book.infrastructure.persistence

import com.letgo.book.domain.Book
import com.letgo.book.domain.BookId
import com.letgo.book.domain.BookRepository
import java.util.*

class InMemoryBookRepository : BookRepository {
    private val storage: MutableMap<BookId, Book> = mutableMapOf()
    override fun find(id: BookId): Optional<Book> {
        return Optional.ofNullable(storage[id])
    }

    override fun save(book: Book) {
        storage[book.id()] = book
    }
}
