package com.letgo.shared.application.bus.query

interface QueryBus {
    fun <Q : Query<R>, R : QueryResponse> dispatch(query: Q): R
}
