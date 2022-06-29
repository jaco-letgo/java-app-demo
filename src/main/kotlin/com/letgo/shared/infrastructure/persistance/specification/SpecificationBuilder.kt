package com.letgo.shared.infrastructure.persistance.specification

import com.letgo.shared.domain.criteria.Filter
import com.letgo.shared.domain.criteria.FilterGroup
import com.letgo.shared.infrastructure.InfrastructureService

@InfrastructureService
class SpecificationBuilder<T>(
    private val strategyFactory: SpecificationStrategyFactory<T>,
) {
    fun build(filterGroup: FilterGroup): Specification<T> {
        val specifications = if (filterGroup.hasFilters()) {
            filterGroup.filters.flatMap { filter -> listOf(buildSpecification(filter)) }
        } else {
            filterGroup.filterGroups.map { build(it) }
        }
        return if (filterGroup.isGroupingAll()) AllSpecifications(specifications)
        else AnySpecification(specifications)
    }

    private fun buildSpecification(filter: Filter<*>): Specification<T> =
        StrategySpecification(filter.value as Any, findStrategy(filter))

    private fun findStrategy(filter: Filter<*>): SpecificationStrategy<T> =
        strategyFactory.create(filter)
}
