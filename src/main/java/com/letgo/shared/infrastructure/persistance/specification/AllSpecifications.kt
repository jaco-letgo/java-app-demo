package com.letgo.shared.infrastructure.persistance.specification

import com.letgo.shared.domain.AggregateRoot

internal class AllSpecifications(
    private val specifications: List<Specification<AggregateRoot>>,
) : Specification<AggregateRoot> {
    override fun isSatisfiedBy(entity: AggregateRoot): Boolean = specifications.all { it.isSatisfiedBy(entity) }
}
