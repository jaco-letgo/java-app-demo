package com.letgo.shared.infrastructure.persistance.specification

import com.letgo.shared.domain.AggregateRoot
import com.letgo.shared.domain.criteria.Behaviour
import com.letgo.shared.domain.criteria.Criteria
import com.letgo.shared.domain.criteria.FilterGroup

class SpecificationBuilder(
    private val specificationFilterMappers: List<SpecificationFilterMapper>,
) {
    fun build(criteria: Criteria): Specification<AggregateRoot> {
        return fromFilterGroup(criteria.filterGroup)
    }

    private fun fromFilterGroup(filterGroup: FilterGroup): Specification<AggregateRoot> {
        val specifications = if (filterGroup.hasFilters()) {
            filterGroup.filters.flatMap { filter ->
                specificationFilterMappers.filter { it.shouldMap(filter) }.map { it.map(filter) }
            }
        } else {
            filterGroup.filterGroups.map { fromFilterGroup(it) }
        } as List<Specification<AggregateRoot>>
        return if (filterGroup.behaviour == Behaviour.All) AllSpecifications(specifications)
        else AnySpecification(specifications)
    }
}
