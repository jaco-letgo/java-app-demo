package com.letgo.book.application;

import com.letgo.book.domain.Book;
import com.letgo.book.domain.BookId;
import com.letgo.book.domain.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class FindBookQueryHandler {
    private final BookRepository repository;

    public FindBookQueryHandler(BookRepository repository) {
        this.repository = repository;
    }

    public FindBookQueryResponse handle(FindBookQuery query) {
        Book book = repository.find(new BookId(query.id()));
        return new FindBookQueryResponse(book.title());
    }
}
