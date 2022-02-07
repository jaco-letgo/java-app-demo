package com.letgo.book.infrastructure.exception

import com.letgo.book.domain.BookNotFound
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class SpringExceptionHandler {
    @ExceptionHandler
    fun handle(exception: BookNotFound): ResponseEntity<String> = ResponseEntity.notFound().build()

    @ExceptionHandler
    fun handle(exception: Exception): ResponseEntity<String> =
        ResponseEntity.internalServerError().body(exception.message)
}
