package com.letgo.shared.domain.criteria

class Criteria private constructor(
    val filterGroup: FilterGroup,
    val pagination: Pagination = Pagination.ofDefaultPageSize(),
) {
    fun paginatedBy(pagination: Pagination): Criteria = Criteria(this.filterGroup, pagination)

    companion object {
        fun matching(filter: Filter<*>): Criteria {
            return Criteria(FilterGroup.withAll(filter))
        }

        fun matchingAll(vararg filters: Filter<*>): Criteria {
            return Criteria(FilterGroup.withAll(*filters))
        }

        fun matchingAny(vararg filters: Filter<*>): Criteria {
            return Criteria(FilterGroup.withAny(*filters))
        }

        fun matchingAll(vararg filterGroups: FilterGroup): Criteria {
            return Criteria(FilterGroup.withAll(*filterGroups))
        }

        fun matchingAny(vararg filterGroups: FilterGroup): Criteria {
            return Criteria(FilterGroup.withAny(*filterGroups))
        }

        fun matchingEverything(): Criteria {
            return Criteria(FilterGroup.empty())
        }
    }
}
