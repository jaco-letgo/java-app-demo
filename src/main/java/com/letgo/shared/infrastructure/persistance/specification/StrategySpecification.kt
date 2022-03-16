package com.letgo.shared.infrastructure.persistance.specification

import com.letgo.shared.domain.AggregateRoot

internal class StrategySpecification(
    private val value: Any,
    private val strategy: (Any, AggregateRoot) -> Boolean,
) : Specification<AggregateRoot> {
    override fun isSatisfiedBy(entity: AggregateRoot): Boolean = strategy(value, entity)
}
