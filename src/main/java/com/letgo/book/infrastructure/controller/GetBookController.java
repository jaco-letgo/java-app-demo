package com.letgo.book.infrastructure.controller;

import com.letgo.book.domain.Book;
import com.letgo.book.domain.BookId;
import com.letgo.book.domain.BookRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetBookController {
    private final BookRepository bookRepository;

    GetBookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/book/{id}")
    public String index(@PathVariable("id") String id) {
        Book book = bookRepository.find(new BookId());
        return book.toString();
    }
}
