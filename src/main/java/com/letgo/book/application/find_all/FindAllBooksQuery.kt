package com.letgo.book.application.find_all

import com.letgo.book.application.BooksResponse
import com.letgo.shared.application.bus.query.Query

class FindAllBooksQuery(
    val pageSize: Int,
    val pageNumber: Int,
) : Query<BooksResponse>
