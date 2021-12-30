package com.letgo.book.infrastructure.controller;

import com.letgo.book.application.create.CreateBookCommand;
import com.letgo.book.application.create.CreateBookCommandHandler;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostBookController {
    private final CreateBookCommandHandler handler;

    PostBookController(CreateBookCommandHandler handler) {
        this.handler = handler;
    }

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public void index(@RequestBody String request) {
        JSONObject body = new JSONObject(request);
        handler.handle(new CreateBookCommand(body.getString("id"), body.getString("title")));
    }
}
