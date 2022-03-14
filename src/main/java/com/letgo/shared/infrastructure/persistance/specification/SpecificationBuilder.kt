package com.letgo.shared.infrastructure.persistance.specification

import com.letgo.shared.domain.criteria.Criteria
import com.letgo.shared.infrastructure.InfrastructureService

@InfrastructureService
class SpecificationBuilder(
    private val specificationFilterMappers: List<SpecificationFilterMapper>,
) {
    fun build(criteria: Criteria): Specification {
        val specifications: List<Specification> = criteria.filterGroup.filters.flatMap { filter ->
            specificationFilterMappers.map {
                it.map(filter)
            }
        }

        return AndSpecification(specifications)
    }
}
