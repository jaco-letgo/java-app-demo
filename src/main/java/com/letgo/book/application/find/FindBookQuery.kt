package com.letgo.book.application.find

import com.letgo.shared.application.bus.query.Query

class FindBookQuery(
    private val id: String
) : Query {
    fun id(): String {
        return id
    }
}
