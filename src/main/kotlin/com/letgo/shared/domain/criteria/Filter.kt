package com.letgo.shared.domain.criteria

class Filter<T> private constructor(
    val field: String,
    val value: T,
    val operator: Operator,
) {
    companion object {
        fun <T> equalTo(field: String, value: T) = Filter(field, value, Operator.Equal)
        fun <T> lessThan(field: String, value: T) = Filter(field, value, Operator.LessThan)
        fun <T> containing(field: String, value: T) = Filter(field, value, Operator.Containing)
    }
}
