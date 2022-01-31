package com.letgo.book.infrastructure.controller

import com.letgo.book.application.changeTitle.ChangeTitleCommand
import com.letgo.shared.application.bus.command.CommandBus
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class PutBookController(
    private val commandBus: CommandBus
) : BookController() {
    @PutMapping("/{id}/title/{title}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun index(@PathVariable id: String, @PathVariable title: String) {
        commandBus.dispatch(ChangeTitleCommand(id, title, LocalDateTime.now().toString()))
    }
}
