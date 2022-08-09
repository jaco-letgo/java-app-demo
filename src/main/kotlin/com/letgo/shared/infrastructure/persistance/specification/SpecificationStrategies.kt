package com.letgo.shared.infrastructure.persistance.specification

import com.letgo.shared.domain.criteria.Operator

interface SpecificationStrategies<T> {
    val map: Map<String, Map<Operator, (Any, T) -> Boolean>>
}
