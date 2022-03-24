package com.letgo.book.application.find_all

import com.letgo.shared.application.bus.query.Query

class FindAllBooksQuery(
    val chunkSize: Int,
    val chunkNumber: Int,
) : Query
