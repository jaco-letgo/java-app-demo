package com.letgo.shared.application.bus.query

interface QueryHandler<T : Query, R : QueryResponse> {
    fun handle(query: T): R
}
