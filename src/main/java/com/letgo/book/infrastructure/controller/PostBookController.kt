package com.letgo.book.infrastructure.controller

import com.letgo.book.application.create.CreateBookCommand
import com.letgo.shared.application.bus.command.CommandBus
import org.json.JSONObject
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class PostBookController(
    private val commandBus: CommandBus
) : BookController() {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Throws(Exception::class)
    fun index(@RequestBody request: String) {
        val body = JSONObject(request)
        commandBus.dispatch(
            CreateBookCommand(
                body.getString("id"),
                body.getString("title"),
                LocalDateTime.now().toString()
            )
        )
    }
}
