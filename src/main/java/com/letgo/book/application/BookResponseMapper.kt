package com.letgo.book.application

import com.letgo.book.domain.Book
import org.springframework.stereotype.Service

@Service
object BookResponseMapper {
    fun map(book: Book): BookResponse = BookResponse(
        id = book.id().value(),
        title = book.title().value(),
        isEdited = book.hasBeenEdited(),
    )
}
