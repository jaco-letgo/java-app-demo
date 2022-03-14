package com.letgo.shared.infrastructure.persistance.specification

interface Specification {
    fun isSatisfiedBy(entity: Any): Boolean
}
