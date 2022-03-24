package com.letgo.book.application.find_all

import com.letgo.book.application.BookResponseMapper
import com.letgo.book.application.BooksResponse
import com.letgo.book.domain.BookRepository
import com.letgo.shared.application.bus.query.QueryHandler
import com.letgo.shared.domain.criteria.Criteria

class FindAllBooksQueryHandler(
    private val repository: BookRepository,
    private val mapper: BookResponseMapper,
) : QueryHandler<FindAllBooksQuery, BooksResponse> {
    override fun handle(query: FindAllBooksQuery): BooksResponse {
        return repository.findBy(
            Criteria.matchingEverything()
            .inChunksOf(query.chunkSize)
            .takingChunkNumber(query.chunkNumber)
        ).map { mapper.map(it) }.let { BooksResponse(it) }
    }
}
