package com.letgo.book.infrastructure.persistence;

import com.letgo.book.domain.Book;
import com.letgo.book.domain.BookId;
import com.letgo.book.domain.BookRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class InMemoryBookRepository implements BookRepository {
    private final Map<BookId, Book> storage = new HashMap<>();

    @Override
    public Optional<Book> find(BookId id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void save(Book book) {
        storage.put(book.id(), book);
    }
}
