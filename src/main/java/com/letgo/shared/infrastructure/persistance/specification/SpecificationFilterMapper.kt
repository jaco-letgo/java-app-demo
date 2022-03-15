package com.letgo.shared.infrastructure.persistance.specification

import com.letgo.shared.domain.AggregateRoot
import com.letgo.shared.domain.criteria.Filter

interface SpecificationFilterMapper {
    fun uses(filter: Filter<*>): Boolean
    fun map(filter: Filter<*>): Specification<out AggregateRoot>
}
