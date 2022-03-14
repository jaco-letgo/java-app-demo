package com.letgo.shared.infrastructure.persistance.specification

import com.letgo.shared.domain.criteria.Filter

interface SpecificationFilterMapper {
    fun shouldMap(filter: Filter): Boolean
    fun map(filter: Filter): Specification
}
