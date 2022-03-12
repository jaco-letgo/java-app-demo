package com.letgo.book.application

import com.letgo.book.application.find.FindBookQueryResponse
import com.letgo.book.domain.Book
import org.springframework.stereotype.Service

@Service
object BookResponseMapper {
    fun map(book: Book): FindBookQueryResponse = FindBookQueryResponse(
        id = book.id().value(),
        title = book.title().value(),
        isEdited = book.hasBeenEdited(),
    )
}
