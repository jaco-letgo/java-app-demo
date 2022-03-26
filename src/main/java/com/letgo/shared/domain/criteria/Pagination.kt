package com.letgo.shared.domain.criteria

private const val DEFAULT_PAGE_SIZE = 100

class Pagination private constructor(
    val size: Int = DEFAULT_PAGE_SIZE,
    pageNumber: Int = 1,
) {
    val elementsToDismiss: Int = size * (pageNumber - 1)

    init {
        if (size < 1) throw IllegalArgumentException("Pagination size must be greater than 0")
        if (pageNumber < 1) throw IllegalArgumentException("Page number must be greater than 0")
    }

    fun showingPage(number: Int): Pagination = Pagination(this.size, number)

    companion object {
        fun ofPageSize(size: Int): Pagination = Pagination(size)
        fun ofDefaultPageSize(): Pagination = Pagination()
    }
}
