package com.letgo.shared.infrastructure.persistance.predicate

import com.letgo.shared.domain.criteria.Filter
import com.letgo.shared.domain.criteria.FilterGroup
import com.letgo.shared.infrastructure.InfrastructureService
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

@InfrastructureService
class PredicateBuilder<T>(
    private val strategyFactory: GenericPredicateStrategyFactory<T>,
) {
    fun build(
        filterGroup: FilterGroup,
        root: Root<T>,
        criteriaBuilder: CriteriaBuilder,
    ): Predicate {
        val predicates = if (filterGroup.hasFilters()) {
            filterGroup.filters.flatMap { filter -> listOf(buildPredicate(filter, root, criteriaBuilder)) }
        } else {
            filterGroup.filterGroups.map { build(it, root, criteriaBuilder) }
        }

        return if (filterGroup.isGroupingAll()) criteriaBuilder.and(*predicates.toTypedArray())
        else criteriaBuilder.or(*predicates.toTypedArray())
    }

    private fun buildPredicate(
        filter: Filter<*>,
        root: Root<T>,
        criteriaBuilder: CriteriaBuilder,
    ): Predicate = strategyFactory.create(filter)(filter, root, criteriaBuilder)
}
