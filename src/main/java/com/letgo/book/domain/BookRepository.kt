package com.letgo.book.domain

import java.util.*

interface BookRepository {
    fun find(id: BookId): Optional<Book>
    fun save(book: Book)
}
