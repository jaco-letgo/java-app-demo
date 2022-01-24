package com.letgo.shared.application.bus.query

interface QueryHandler<T : Query> {
    @Throws(Exception::class)
    fun handle(query: T): QueryResponse
}
