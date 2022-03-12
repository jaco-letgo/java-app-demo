package com.letgo.book.application.find

import com.letgo.shared.application.bus.query.QueryResponse

data class FindBookQueryResponse(
    val id: String,
    val title: String,
    val isEdited: Boolean,
) : QueryResponse
