package com.letgo.book.infrastructure.controller

import com.letgo.book.application.BooksResponse
import com.letgo.book.application.find_by.FindBooksByCriteriaQuery
import com.letgo.shared.application.bus.query.QueryBus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GetCreatedBooksAndIdController(
    private val queryBus: QueryBus,
) : BookController() {
    @GetMapping("/with/id/{id}")
    fun index(@PathVariable("id") id: String): ResponseEntity<BooksResponse> =
        ResponseEntity.ok().body(queryBus.dispatch(FindBooksByCriteriaQuery(id)))
}
