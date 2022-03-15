package com.letgo.shared.domain.criteria

class FilterGroup private constructor(
    val behaviour: Behaviour,
    val filterGroups: List<FilterGroup> = listOf(),
    val filters: List<Filter<*>> = listOf(),
) {
    init {
        if (hasFilters() && filterGroups.isNotEmpty()) {
            throw IllegalArgumentException("It can't have both filters and filter groups")
        }
        if (!hasFilters() && filterGroups.isEmpty()) {
            throw IllegalArgumentException("It must have any filter or filter groups")
        }
    }

    fun hasFilters() = filters.isNotEmpty()

    companion object {
        fun withAll(vararg filterGroups: FilterGroup): FilterGroup {
            return FilterGroup(Behaviour.All, filterGroups = filterGroups.toList())
        }

        fun withAny(vararg filterGroups: FilterGroup): FilterGroup {
            return FilterGroup(Behaviour.Any, filterGroups = filterGroups.toList())
        }

        fun withAll(vararg filters: Filter<*>): FilterGroup {
            return FilterGroup(Behaviour.All, filters = filters.toList())
        }

        fun withAny(vararg filters: Filter<*>): FilterGroup {
            return FilterGroup(Behaviour.Any, filters = filters.toList())
        }
    }
}
