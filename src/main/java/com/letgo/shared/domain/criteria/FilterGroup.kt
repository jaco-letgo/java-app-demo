package com.letgo.shared.domain.criteria

class FilterGroup private constructor(
    val behaviour: Behaviour,
    val filterGroups: List<FilterGroup> = listOf(),
    val filters: List<Filter> = listOf(),
) {
    companion object {
        fun withAll(vararg filterGroups: FilterGroup): FilterGroup {
            return FilterGroup(Behaviour.All, filterGroups = filterGroups.toList())
        }

        fun withAny(vararg filterGroups: FilterGroup): FilterGroup {
            return FilterGroup(Behaviour.Any, filterGroups = filterGroups.toList())
        }

        fun withAll(vararg filters: Filter): FilterGroup {
            return FilterGroup(Behaviour.All, filters = filters.toList())
        }

        fun withAny(vararg filters: Filter): FilterGroup {
            return FilterGroup(Behaviour.Any, filters = filters.toList())
        }
    }
}
