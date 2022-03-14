package com.letgo.shared.infrastructure.persistance.specification

import com.letgo.shared.domain.criteria.Filter

interface SpecificationFilterMapper {
    fun map(filter: Filter): Specification
}
