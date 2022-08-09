package com.letgo.shared.infrastructure.persistance.specification

internal class AllSpecifications<T>(
    private val specifications: List<Specification<T>>,
) : Specification<T> {
    override fun isSatisfiedBy(entity: T): Boolean = specifications.all { it.isSatisfiedBy(entity) }
}
