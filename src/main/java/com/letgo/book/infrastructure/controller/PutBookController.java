package com.letgo.book.infrastructure.controller;

import com.letgo.book.application.changeTitle.ChangeTitleCommand;
import com.letgo.shared.application.bus.command.CommandBus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PutBookController {
    private final CommandBus commandBus;

    public PutBookController(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @PutMapping("/book/{id}/title/{title}")
    @ResponseStatus(HttpStatus.OK)
    public void index(@PathVariable String id, @PathVariable String title) throws Exception {
        commandBus.dispatch(new ChangeTitleCommand(id, title));
    }
}
