package com.letgo.book.application.find

import com.letgo.book.application.BookResponse
import com.letgo.shared.application.bus.query.Query

class FindBookQuery(
    val id: String
) : Query<BookResponse>
