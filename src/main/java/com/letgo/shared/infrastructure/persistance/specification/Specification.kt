package com.letgo.shared.infrastructure.persistance.specification

import com.letgo.shared.domain.AggregateRoot

interface Specification<T : AggregateRoot> {
    fun isSatisfiedBy(entity: T): Boolean
}
