package com.letgo.shared.infrastructure.persistance.specification

internal class StrategySpecification<T>(
    private val value: Any,
    private val strategy: SpecificationStrategy<T>,
) : Specification<T> {
    override fun isSatisfiedBy(entity: T): Boolean = strategy(value, entity)
}
