package com.letgo.shared.domain.criteria

class Filter<T> private constructor(
    val field: Enum<*>,
    val value: T,
    val operator: Operator,
) {
    companion object {
        fun <T> equalTo(field: Enum<*>, value: T) = Filter(field, value, Operator.Equal)
        fun <T> lessThan(field: Enum<*>, value: T) = Filter(field, value, Operator.LessThan)
        fun <T> containing(field: Enum<*>, value: T) = Filter(field, value, Operator.Containing)
    }
}
