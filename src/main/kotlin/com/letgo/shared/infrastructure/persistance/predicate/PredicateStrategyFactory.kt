package com.letgo.shared.infrastructure.persistance.predicate

import com.letgo.shared.domain.criteria.Filter
import com.letgo.shared.domain.criteria.Operator
import com.letgo.shared.infrastructure.InfrastructureService
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

typealias PredicateStrategy<T> = (Filter<*>, Root<T>, CriteriaBuilder) -> Predicate

@InfrastructureService
class GenericPredicateStrategyFactory<T> {
    fun create(filter: Filter<*>): PredicateStrategy<T> = when (filter.operator) {
        Operator.Equal -> equalsTo()
        Operator.LessThan -> filter.notImplemented()
        Operator.Containing -> contains()
    }

    private fun Filter<*>.notImplemented(): Nothing =
        throw NoSuchElementException("strategy not found for $field ${operator.name} operation")

    private fun contains() = { filter: Filter<*>, root: Root<T>, cb: CriteriaBuilder ->
        cb.like(root.get<String>(filter.field).get("value"), "%${filter.value}%")
    }

    private fun equalsTo() = { filter: Filter<*>, root: Root<T>, cb: CriteriaBuilder ->
        cb.equal(root.get<String>(filter.field), filter.value)
    }
}
