package com.letgo.book.infrastructure.controller

import com.letgo.book.application.find.FindBookQuery
import com.letgo.book.application.find.FindBookQueryResponse
import com.letgo.shared.application.bus.query.QueryBus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GetBookController(
    private val queryBus: QueryBus,
) : BookController() {
    @GetMapping("/{id}")
    fun index(@PathVariable("id") id: String): ResponseEntity<FindBookQueryResponse> {
        val response = queryBus.dispatch(FindBookQuery(id)) as FindBookQueryResponse
        return ResponseEntity.ok().body(response)
    }
}
