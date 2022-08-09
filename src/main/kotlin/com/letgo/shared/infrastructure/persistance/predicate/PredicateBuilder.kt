package com.letgo.shared.infrastructure.persistance.predicate

import com.letgo.shared.domain.criteria.Criteria
import com.letgo.shared.domain.criteria.Filter
import com.letgo.shared.domain.criteria.FilterGroup
import com.letgo.shared.infrastructure.InfrastructureService
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

@InfrastructureService
class PredicateBuilder<T>(
    private val strategies: PredicateStrategies<T>,
) {
    fun build(
        criteria: Criteria,
        root: Root<T>,
        criteriaBuilder: CriteriaBuilder,
    ): Predicate = fromFilterGroup(criteria.filterGroup, root, criteriaBuilder)

    private fun fromFilterGroup(
        filterGroup: FilterGroup,
        root: Root<T>,
        criteriaBuilder: CriteriaBuilder,
    ): Predicate {
        val predicates = if (filterGroup.hasFilters()) {
            filterGroup.filters.flatMap { filter -> listOf(buildPredicate(filter, root, criteriaBuilder)) }
        } else {
            filterGroup.filterGroups.map { fromFilterGroup(it, root, criteriaBuilder) }
        }

        return if (filterGroup.isGroupingAll()) criteriaBuilder.and(*predicates.toTypedArray())
        else criteriaBuilder.or(*predicates.toTypedArray())
    }

    private fun buildPredicate(
        filter: Filter,
        root: Root<T>,
        criteriaBuilder: CriteriaBuilder,
    ): Predicate = findStrategy(filter).invoke(filter, root, criteriaBuilder)

    private fun findStrategy(filter: Filter): (Filter, Root<T>, CriteriaBuilder) -> Predicate =
        strategies.map[filter.name]?.get(filter.operator) ?: throw NoSuchElementException(
            "strategy not found for ${filter.name} ${filter.operator.name} operation"
        )
}
