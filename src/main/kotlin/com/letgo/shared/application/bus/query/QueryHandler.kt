package com.letgo.shared.application.bus.query

interface QueryHandler<Q : Query<R>, R : QueryResponse> {
    fun handle(query: Q): R
}
