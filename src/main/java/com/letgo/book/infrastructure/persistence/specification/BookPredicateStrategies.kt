package com.letgo.book.infrastructure.persistence.specification

import com.letgo.book.domain.Book
import com.letgo.book.domain.BookId
import com.letgo.book.domain.BookStatus
import com.letgo.book.domain.BookTitle
import com.letgo.shared.domain.criteria.Filter
import com.letgo.shared.domain.criteria.Operator
import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.persistance.predicate.PredicateStrategies
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

@InfrastructureService
object BookPredicateStrategies : PredicateStrategies<Book> {
    override val map: Map<String, Map<Operator, (Filter, Root<Book>, CriteriaBuilder) -> Predicate>> = mapOf(
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

    private fun equalsBookId() = { filter: Filter, root: Root<Book>, cb: CriteriaBuilder ->
        cb.equal(root.get<BookId>("id"), filter.value)
    }

    private fun equalsBookStatus() = { filter: Filter, root: Root<Book>, cb: CriteriaBuilder ->
        cb.equal(root.get<BookStatus>("status"), filter.value)
    }

    private fun equalsBookTitle() = { filter: Filter, root: Root<Book>, cb: CriteriaBuilder ->
        cb.equal(root.get<BookTitle>("title"), filter.value)
    }

    private fun containsBookTitle() = { filter: Filter, root: Root<Book>, cb: CriteriaBuilder ->
        cb.like(root.get<BookTitle>("title").get("value"), "%${filter.value}%")
    }
}
