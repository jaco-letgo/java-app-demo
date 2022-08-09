package com.letgo.book.infrastructure.controller

import com.letgo.book.application.BooksResponse
import com.letgo.book.application.find_all.FindAllBooksQuery
import com.letgo.shared.application.bus.query.QueryBus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GetAllBooksController(
    private val queryBus: QueryBus,
) : BookController() {
    @GetMapping
    fun index(@RequestParam elements: Int?, @RequestParam page: Int?): ResponseEntity<BooksResponse> =
        ResponseEntity.ok().body(
            queryBus.dispatch(
                FindAllBooksQuery(
                    pageSize = elements ?: 100,
                    pageNumber = page ?: 1,
                )
            )
        )
}
