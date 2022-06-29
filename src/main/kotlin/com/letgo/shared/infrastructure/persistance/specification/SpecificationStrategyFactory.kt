package com.letgo.shared.infrastructure.persistance.specification

import com.letgo.shared.domain.criteria.Filter

typealias SpecificationStrategy<T> = (Any, T) -> Boolean

interface SpecificationStrategyFactory<T> {
    fun create(filter: Filter<*>): SpecificationStrategy<T>
}
