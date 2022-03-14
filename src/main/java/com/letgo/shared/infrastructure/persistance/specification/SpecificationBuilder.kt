package com.letgo.shared.infrastructure.persistance.specification

import com.letgo.shared.domain.criteria.Behaviour
import com.letgo.shared.domain.criteria.Criteria
import com.letgo.shared.domain.criteria.FilterGroup

class SpecificationBuilder(
    private val specificationFilterMappers: List<SpecificationFilterMapper>,
) {
    fun build(criteria: Criteria): Specification {
        return fromFilterGroup(criteria.filterGroup)
    }

    private fun fromFilterGroup(filterGroup: FilterGroup): Specification {
        val specifications = if (filterGroup.filters.isNotEmpty()) {
            filterGroup.filters.flatMap { filter ->
                specificationFilterMappers.filter { it.shouldMap(filter) }.map { it.map(filter) }
            }
        } else {
            filterGroup.filterGroups.map { fromFilterGroup(it) }
        }
        return if (filterGroup.behaviour == Behaviour.All) AndSpecification(specifications)
        else OrSpecification(specifications)
    }
}
