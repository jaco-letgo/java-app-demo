package com.letgo.shared.application.bus.query

interface QueryHandler<T : Query> {
    fun handle(query: T): QueryResponse
}
