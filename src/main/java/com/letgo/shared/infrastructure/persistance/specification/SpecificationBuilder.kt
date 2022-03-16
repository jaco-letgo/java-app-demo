package com.letgo.shared.infrastructure.persistance.specification

import com.letgo.shared.domain.AggregateRoot
import com.letgo.shared.domain.criteria.Behaviour
import com.letgo.shared.domain.criteria.Criteria
import com.letgo.shared.domain.criteria.Filter
import com.letgo.shared.domain.criteria.FilterGroup
import com.letgo.shared.infrastructure.InfrastructureService

@InfrastructureService
class SpecificationBuilder(repositories: List<SpecificationStrategies>) {
    private val specificationStrategyRepository = repositories.reduce { current, next ->
        object : SpecificationStrategies {
            override val map = current.map + next.map
        }
    }

    fun build(criteria: Criteria): Specification<AggregateRoot> {
        return fromFilterGroup(criteria.filterGroup)
    }

    private fun fromFilterGroup(filterGroup: FilterGroup): Specification<AggregateRoot> {
        val specifications = if (filterGroup.hasFilters()) {
            filterGroup.filters.flatMap { filter -> listOf(buildSpecification(filter)) }
        } else {
            filterGroup.filterGroups.map { fromFilterGroup(it) }
        }
        return if (filterGroup.behaviour == Behaviour.All) AllSpecifications(specifications)
        else AnySpecification(specifications)
    }

    private fun buildSpecification(filter: Filter): Specification<AggregateRoot> {
        val strategy = specificationStrategyRepository.map[filter.name]?.get(filter.operator)
            ?: throw NoSuchElementException(
                "specification not found for ${filter.name} ${filter.operator.name} operation"
            )
        return StrategySpecification(filter.value, strategy)
    }
}
