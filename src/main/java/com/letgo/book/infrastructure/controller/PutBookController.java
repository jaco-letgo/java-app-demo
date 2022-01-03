package com.letgo.book.infrastructure.controller;

import com.letgo.book.application.changeTitle.ChangeTitleCommand;
import com.letgo.book.application.changeTitle.ChangeTitleCommandHandler;
import com.letgo.book.domain.BookNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PutBookController {
    private final ChangeTitleCommandHandler handler;

    PutBookController(ChangeTitleCommandHandler handler) {
        this.handler = handler;
    }

    @PutMapping("/book/{id}/title/{title}")
    @ResponseStatus(HttpStatus.OK)
    public void index(@PathVariable String id, @PathVariable String title) {
        handler.handle(new ChangeTitleCommand(id, title));
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
