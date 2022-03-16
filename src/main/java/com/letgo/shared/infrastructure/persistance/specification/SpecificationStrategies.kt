package com.letgo.shared.infrastructure.persistance.specification

import com.letgo.shared.domain.AggregateRoot
import com.letgo.shared.domain.criteria.Operator

interface SpecificationStrategies {
    val map: Map<String, Map<Operator, (Any, AggregateRoot) -> Boolean>>
}
