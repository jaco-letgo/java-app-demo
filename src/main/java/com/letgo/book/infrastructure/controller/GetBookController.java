package com.letgo.book.infrastructure.controller;

import com.letgo.book.application.FindBookQuery;
import com.letgo.book.application.FindBookQueryHandler;
import com.letgo.book.application.FindBookQueryResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetBookController {
    private final FindBookQueryHandler handler;

    GetBookController(FindBookQueryHandler handler) {
        this.handler = handler;
    }

    @GetMapping("/book/{id}")
    public String index(@PathVariable("id") String id) {
        FindBookQueryResponse response = handler.handle(new FindBookQuery(id));
        return response.title();
    }
}
