package com.letgo.book.application

import com.letgo.book.domain.Book

fun Book.toResponse(): BookResponse = BookResponse(
    id = id().value(),
    title = title().value(),
    isEdited = hasBeenEdited(),
)

fun List<Book>.toResponse(): BooksResponse = BooksResponse(map { it.toResponse() })
