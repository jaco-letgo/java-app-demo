package com.letgo.book.infrastructure.persistence.strategy

import com.letgo.book.domain.Book
import com.letgo.book.domain.BookId
import com.letgo.book.domain.BookStatus
import com.letgo.book.domain.BookTitle
import com.letgo.shared.domain.criteria.Filter
import com.letgo.shared.domain.criteria.Operator
import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.persistance.specification.SpecificationStrategy
import com.letgo.shared.infrastructure.persistance.specification.SpecificationStrategyFactory

@InfrastructureService
class BookSpecificationStrategyFactory : SpecificationStrategyFactory<Book> {
    override fun create(filter: Filter<*>): SpecificationStrategy<Book> =
        when (filter.field) {
            "id" -> when (filter.operator) {
                Operator.Equal -> equalsBookId()
                Operator.LessThan -> filter.notImplemented()
                Operator.Containing -> filter.notImplemented()
            }
            "status" -> when (filter.operator) {
                Operator.Equal -> equalsBookStatus()
                Operator.LessThan -> filter.notImplemented()
                Operator.Containing -> filter.notImplemented()
            }
            "title" -> when (filter.operator) {
                Operator.Equal -> equalsBookTitle()
                Operator.LessThan -> filter.notImplemented()
                Operator.Containing -> containsBookTitle()
            }
            else -> filter.notImplemented()
        }

    private fun Filter<*>.notImplemented(): Nothing =
        throw NoSuchElementException("strategy not found for $field ${operator.name} operation")

    private fun equalsBookId() = { id: BookId, book: Book ->
        id == book.id()
    } as SpecificationStrategy<Book>

    private fun equalsBookStatus() = { status: BookStatus, book: Book ->
        status == if (book.hasBeenEdited()) BookStatus.Edited else BookStatus.Created
    } as SpecificationStrategy<Book>

    private fun equalsBookTitle() = { title: BookTitle, book: Book ->
        title == book.title()
    } as SpecificationStrategy<Book>

    private fun containsBookTitle() = { titleExcerpt: BookTitle, book: Book ->
        book.title().value().contains(titleExcerpt.value(), true)
    } as SpecificationStrategy<Book>
}
