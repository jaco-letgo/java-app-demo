package com.letgo.shared.infrastructure.persistance.specification

import com.letgo.shared.domain.AggregateRoot

internal class AnySpecification(
    private val specifications: List<Specification<AggregateRoot>>,
) : Specification<AggregateRoot> {
    override fun isSatisfiedBy(entity: AggregateRoot): Boolean = specifications.any { it.isSatisfiedBy(entity) }
}
