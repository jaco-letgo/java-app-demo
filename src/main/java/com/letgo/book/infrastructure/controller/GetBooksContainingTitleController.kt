package com.letgo.book.infrastructure.controller

import com.letgo.book.application.BooksResponse
import com.letgo.book.application.find_containing_title.FindBooksContainingTitleQuery
import com.letgo.shared.application.bus.query.QueryBus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GetBooksContainingTitleController(
    private val queryBus: QueryBus,
) : BookController() {
    @GetMapping("/containing/title/{excerpt}")
    fun index(@PathVariable("excerpt") excerpt: String): ResponseEntity<BooksResponse> {
        val response = queryBus.dispatch(FindBooksContainingTitleQuery(excerpt)) as BooksResponse
        return ResponseEntity.ok().body(response)
    }
}
