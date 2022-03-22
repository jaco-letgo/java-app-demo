package com.letgo.book.infrastructure.persistence.specification

import com.letgo.book.domain.Book
import com.letgo.book.domain.BookId
import com.letgo.book.domain.BookStatus
import com.letgo.book.domain.BookTitle
import com.letgo.shared.domain.criteria.Operator
import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.persistance.specification.SpecificationStrategies

@InfrastructureService
object BookSpecificationStrategies : SpecificationStrategies<Book> {
    override val map: Map<String, Map<Operator, (Any, Book) -> Boolean>> = mapOf(
        "book_id" to mapOf(
            Operator.Equal to equalsBookId(),
        ),
        "book_status" to mapOf(
            Operator.Equal to equalsBookStatus(),
        ),
        "book_title" to mapOf(
            Operator.Equal to equalsBookTitle(),
            Operator.Containing to containsBookTitle(),
        ),
    )

    private fun equalsBookId() = { id: BookId, book: Book ->
        id == book.id()
    } as (Any, Book) -> Boolean

    private fun equalsBookStatus() = { status: BookStatus, book: Book ->
        status == if (book.hasBeenEdited()) BookStatus.Edited else BookStatus.Created
    } as (Any, Book) -> Boolean

    private fun equalsBookTitle() = { title: BookTitle, book: Book ->
        title == book.title()
    } as (Any, Book) -> Boolean

    private fun containsBookTitle() = { titleExcerpt: String, book: Book ->
        book.title().value().contains(titleExcerpt)
    } as (Any, Book) -> Boolean
}
