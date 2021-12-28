package com.letgo.book.infrastructure.controller;

import com.letgo.book.application.find.FindBookQuery;
import com.letgo.book.application.find.FindBookQueryHandler;
import com.letgo.book.application.find.FindBookQueryResponse;
import com.letgo.book.domain.BookNotFound;
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
        try {
            FindBookQueryResponse response = handler.handle(new FindBookQuery(id));
            return response.title();
        } catch (BookNotFound exception) {
            return exception.getMessage();
        }
    }
}
