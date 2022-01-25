package com.letgo.book.application.find

import com.letgo.shared.application.bus.query.QueryResponse

class FindBookQueryResponse(
    private val title: String
) : QueryResponse {
    fun title(): String {
        return title
    }
}
