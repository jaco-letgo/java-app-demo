package com.letgo.book.application.find

import com.letgo.shared.application.bus.query.Query

data class FindBookQuery(
    val id: String
) : Query
