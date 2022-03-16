package com.letgo.book.infrastructure.persistence.specification

import com.letgo.book.domain.Book
import com.letgo.book.domain.BookId
import com.letgo.book.domain.BookStatus
import com.letgo.shared.domain.AggregateRoot
import com.letgo.shared.domain.criteria.Operator
import com.letgo.shared.infrastructure.persistance.specification.SpecificationStrategies

object BookSpecificationStrategies : SpecificationStrategies {
    override val map: Map<String, Map<Operator, (Any, AggregateRoot) -> Boolean>> = mapOf(
        "book_id" to mapOf(
            Operator.Equal to equalsBookId() as (Any, AggregateRoot) -> Boolean,
        ),
        "book_status" to mapOf(
            Operator.Equal to equalsBookStatus() as (Any, AggregateRoot) -> Boolean,
        ),
    )

    private fun equalsBookStatus() = { value: BookStatus, entity: Book ->
        value == if (entity.hasBeenEdited()) BookStatus.Edited else BookStatus.Created
    }

    private fun equalsBookId() = { value: BookId, entity: Book -> value == entity.id() }
}
