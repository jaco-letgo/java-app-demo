package com.letgo.book.infrastructure.controller

import com.letgo.book.application.find_all.FindAllBooksQuery
import com.letgo.book.application.BooksQueryResponse
import com.letgo.shared.application.bus.query.QueryBus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GetAllBooksController(
    private val queryBus: QueryBus,
) : BookController() {
    @GetMapping
    fun index(): ResponseEntity<BooksQueryResponse> {
        val response = queryBus.dispatch(FindAllBooksQuery()) as BooksQueryResponse
        return ResponseEntity.ok().body(response)
    }
}
