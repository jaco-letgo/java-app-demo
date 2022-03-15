package com.letgo.shared.domain.criteria

class Filter<T>(
    val value: T,
    val operator: Operator,
) {
    companion object {
        fun <T> equalTo(value: T) = Filter(value, Operator.Equal)
        fun <T> lessThan(value: T): Filter<T> {
            if (value !is Comparable<*>) {
                throw Exception("no comparable")
            }
            return Filter(value, Operator.LessThan)
        }
    }
}
