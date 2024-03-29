package com.letgo.book.application.find_containing_title

import com.letgo.book.application.BooksResponse
import com.letgo.shared.application.bus.query.Query

data class FindBooksContainingTitleQuery(
    val titleExcerpt: String,
) : Query<BooksResponse>
