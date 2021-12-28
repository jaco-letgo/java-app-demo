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
    private final Map<String, Book> storage = new HashMap<>();

    @Override
    public Optional<Book> find(BookId id) {
        if (storage.containsKey(id.value())) {
            return Optional.of(storage.get(id.value()));
        }
        return Optional.empty();
    }

    @Override
    public void save(Book book) {
        storage.put(book.id().value(), book);
    }
}
