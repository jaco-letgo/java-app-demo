package com.letgo.book.application.find_by

import com.letgo.book.application.BooksResponse
import com.letgo.shared.application.bus.query.Query

data class FindBooksByCriteriaQuery(
    val bookId: String,
) : Query<BooksResponse>
