package com.letgo.shared.application.bus.query

interface QueryBus {
    fun dispatch(query: Query): QueryResponse
}
