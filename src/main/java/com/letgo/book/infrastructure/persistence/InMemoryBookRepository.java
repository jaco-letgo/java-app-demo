package com.letgo.book.infrastructure.persistence;

import com.letgo.book.domain.Book;
import com.letgo.book.domain.BookId;
import com.letgo.book.domain.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InMemoryBookRepository implements BookRepository {
    @Override
    public Optional<Book> find(BookId id) {
        return Optional.of(new Book(id, "title"));
    }
}
