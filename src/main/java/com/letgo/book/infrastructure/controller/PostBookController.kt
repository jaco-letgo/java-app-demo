package com.letgo.book.infrastructure.controller

import com.letgo.book.application.create.CreateBookCommand
import com.letgo.shared.application.bus.command.CommandBus
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class PostBookController(
    private val commandBus: CommandBus,
) : BookController() {
    @PostMapping(consumes = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun index(@RequestBody request: Request) {
        commandBus.dispatch(
            CreateBookCommand(
                request.id,
                request.title,
                LocalDateTime.now().toString()
            )
        )
    }

    data class Request(val id: String, val title: String)
}
