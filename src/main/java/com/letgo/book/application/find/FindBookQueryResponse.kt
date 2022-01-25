package com.letgo.book.application.find

import com.letgo.shared.application.bus.query.QueryResponse

data class FindBookQueryResponse(
    val title: String
) : QueryResponse
