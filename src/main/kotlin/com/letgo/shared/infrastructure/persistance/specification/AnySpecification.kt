package com.letgo.shared.infrastructure.persistance.specification

internal class AnySpecification<T>(
    private val specifications: List<Specification<T>>,
) : Specification<T> {
    override fun isSatisfiedBy(entity: T): Boolean = specifications.any { it.isSatisfiedBy(entity) }
}
