package com.letgo.book.infrastructure.controller;

import com.letgo.book.application.create.CreateBookCommand;
import com.letgo.shared.application.bus.command.CommandBus;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostBookController {
    private final CommandBus commandBus;

    public PostBookController(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public void index(@RequestBody String request) throws Exception {
        JSONObject body = new JSONObject(request);
        commandBus.dispatch(new CreateBookCommand(body.getString("id"), body.getString("title")));
    }
}
