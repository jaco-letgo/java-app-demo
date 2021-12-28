package com.letgo.book.application.find;

import com.letgo.book.domain.Book;
import com.letgo.book.domain.BookId;
import com.letgo.book.domain.BookNotFound;
import com.letgo.book.domain.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindBookQueryHandler {
    private final BookRepository repository;

    public FindBookQueryHandler(BookRepository repository) {
        this.repository = repository;
    }

    public FindBookQueryResponse handle(FindBookQuery query) {
        Optional<Book> book = repository.find(new BookId(query.id()));
        if (book.isEmpty()) {
            throw BookNotFound.withId(query.id());
        }
        return new FindBookQueryResponse(book.get().title());
    }
}
