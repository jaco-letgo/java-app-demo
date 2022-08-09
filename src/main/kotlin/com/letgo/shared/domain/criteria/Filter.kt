package com.letgo.shared.domain.criteria

interface Filter {
    val name: String
    val value: Any
    val operator: Operator
}
