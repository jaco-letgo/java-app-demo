package com.letgo.book.infrastructure.controller

import com.letgo.book.application.changeTitle.ChangeTitleCommand
import com.letgo.shared.application.bus.command.CommandBus
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class PutBookController(
    private val commandBus: CommandBus
) : BookController() {
    @PutMapping(path = ["/{id}"], consumes = ["application/json"])
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun index(@PathVariable id: String, @RequestBody request: Request) {
        commandBus.dispatch(ChangeTitleCommand(id, request.title, LocalDateTime.now().toString()))
    }

    data class Request(val title: String)
}
