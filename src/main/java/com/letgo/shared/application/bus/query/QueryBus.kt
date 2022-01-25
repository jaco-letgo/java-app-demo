package com.letgo.shared.application.bus.query

interface QueryBus {
    @Throws(Exception::class)
    fun dispatch(query: Query): QueryResponse
}
