package com.letgo.book.infrastructure.persistence

import com.letgo.book.domain.Book
import com.letgo.book.domain.BookId
import com.letgo.book.domain.BookRepository
import com.letgo.shared.domain.OptimisticConcurrencyLock

class InMemoryBookRepository : BookRepository {
    private val storage: MutableMap<BookId, Book> = mutableMapOf()

    override fun all(): List<Book> = storage.values.toList().map { it.duplicate() }

    override fun find(id: BookId): Book? {
        return storage[id]?.duplicate()
    }

    override fun save(book: Book) {
        if (storage.contains(book.id())) {
            storeWithOptimisticConcurrency(book)
        } else {
            store(book)
        }
    }

    private fun storeWithOptimisticConcurrency(book: Book) {
        synchronized(storage[book.id()]!!) {
            if (!book.hasCurrentVersion()) {
                throw OptimisticConcurrencyLock()
            }
            store(book)
        }
    }

    private fun Book.hasCurrentVersion() = version() == storage[id()]?.version()

    private fun store(book: Book) {
        storage[book.id()] = book.duplicate().apply {
            increaseVersion()
        }
    }

    private fun Book.duplicate() = copy().also { book ->
        repeat(version()) { book.increaseVersion() }
        book.retrieveEvents()
    }
}
