package com.letgo.book.infrastructure.controller;

import com.letgo.book.application.find.FindBookQuery;
import com.letgo.book.application.find.FindBookQueryResponse;
import com.letgo.book.domain.BookNotFound;
import com.letgo.shared.application.bus.query.QueryBus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetBookController {
    private final QueryBus queryBus;

    public GetBookController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<String> index(@PathVariable("id") String id) throws Exception {
        FindBookQueryResponse response = (FindBookQueryResponse) queryBus.dispatch(new FindBookQuery(id));
        return ResponseEntity.accepted().body(response.title());
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(BookNotFound exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(Exception exception) {
        return ResponseEntity.internalServerError().build();
    }
}
