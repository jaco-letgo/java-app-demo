package com.letgo.book.application

import com.letgo.shared.application.bus.query.QueryResponse

data class BookResponse(
    val id: String,
    val title: String,
    val isEdited: Boolean,
) : QueryResponse
