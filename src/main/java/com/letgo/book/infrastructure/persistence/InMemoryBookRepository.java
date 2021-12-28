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
        Book book = new Book(id, "title");
        save(book);
        if (storage.containsKey(id)) {
            return Optional.of(storage.get(id));
        }
        return Optional.empty();
    }

    @Override
    public void save(Book book) {
        storage.put(book.id(), book);
    }
}
