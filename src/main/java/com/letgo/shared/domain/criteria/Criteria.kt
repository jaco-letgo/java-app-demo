package com.letgo.shared.domain.criteria

class Criteria private constructor(
    val filterGroup: FilterGroup,
    private val chunkNumber: Int = 0,
    val chunkSize: Int = Int.MAX_VALUE,
) {
    fun takingChunkNumber(position: Int): Criteria = Criteria(this.filterGroup, position, this.chunkSize)
    fun inChunksOf(size: Int): Criteria = Criteria(this.filterGroup, this.chunkNumber, size)
    fun hasLimit(): Boolean = chunkSize < Int.MAX_VALUE
    fun dismiss(): Int = chunkNumber * if (hasLimit()) chunkSize else 1

    companion object {
        fun matching(filter: Filter): Criteria {
            return Criteria(FilterGroup.withAll(filter))
        }

        fun matchingAll(vararg filters: Filter): Criteria {
            return Criteria(FilterGroup.withAll(*filters))
        }

        fun matchingAny(vararg filters: Filter): Criteria {
            return Criteria(FilterGroup.withAny(*filters))
        }

        fun matchingAll(vararg filterGroups: FilterGroup): Criteria {
            return Criteria(FilterGroup.withAll(*filterGroups))
        }

        fun matchingAny(vararg filterGroups: FilterGroup): Criteria {
            return Criteria(FilterGroup.withAny(*filterGroups))
        }

        fun matchingEverything(): Criteria {
            return Criteria(FilterGroup.withAll(*emptyArray<Filter>()))
        }
    }
}
