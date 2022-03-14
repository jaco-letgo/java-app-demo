package com.letgo.shared.infrastructure.persistance.specification

class AndSpecification(
    private val specifications: List<Specification>,
) : Specification {
    override fun isSatisfiedBy(entity: Any): Boolean = specifications.all { it.isSatisfiedBy(entity) }
}
