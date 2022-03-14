package com.letgo.shared.infrastructure.persistance.specification

class OrSpecification(
    private val specifications: List<Specification>,
) : Specification {
    override fun isSatisfiedBy(entity: Any): Boolean = specifications.any { it.isSatisfiedBy(entity) }
}
