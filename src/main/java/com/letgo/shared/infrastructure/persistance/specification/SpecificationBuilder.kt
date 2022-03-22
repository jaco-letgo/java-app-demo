package com.letgo.shared.infrastructure.persistance.specification

import com.letgo.shared.domain.criteria.Criteria
import com.letgo.shared.domain.criteria.Filter
import com.letgo.shared.domain.criteria.FilterGroup
import com.letgo.shared.infrastructure.InfrastructureService

@InfrastructureService
class SpecificationBuilder<T>(
    private val strategies: SpecificationStrategies<T>,
) {
    fun build(criteria: Criteria): Specification<T> {
        return fromFilterGroup(criteria.filterGroup)
    }

    private fun fromFilterGroup(filterGroup: FilterGroup): Specification<T> {
        val specifications = if (filterGroup.hasFilters()) {
            filterGroup.filters.flatMap { filter -> listOf(buildSpecification(filter)) }
        } else {
            filterGroup.filterGroups.map { fromFilterGroup(it) }
        }
        return if (filterGroup.isGroupingAll()) AllSpecifications(specifications)
        else AnySpecification(specifications)
    }

    private fun buildSpecification(filter: Filter): Specification<T> {
        return StrategySpecification(filter.value, findStrategy(filter))
    }

    private fun findStrategy(filter: Filter): (Any, T) -> Boolean {
        return strategies.map[filter.name]?.get(filter.operator) ?: throw NoSuchElementException(
            "strategy not found for ${filter.name} ${filter.operator.name} operation"
        )
    }
}
