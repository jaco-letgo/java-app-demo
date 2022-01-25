package com.letgo.book.infrastructure.exception;

import com.letgo.book.domain.BookNotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
final public class SpringExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<String> handle(BookNotFound exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(Exception exception) {
        return ResponseEntity.internalServerError().body(exception.getMessage());
    }
}
