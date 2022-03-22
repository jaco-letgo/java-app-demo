package com.letgo.shared.infrastructure.persistance.specification

interface Specification<T> {
    fun isSatisfiedBy(entity: T): Boolean
}
