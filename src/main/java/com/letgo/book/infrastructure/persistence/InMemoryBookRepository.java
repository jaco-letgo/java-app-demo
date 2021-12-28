package com.letgo.book.infrastructure.persistence;

import com.letgo.book.domain.Book;
import com.letgo.book.domain.BookId;
import com.letgo.book.domain.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class InMemoryBookRepository implements BookRepository {
    @Override
    public Book find(BookId id) {
        return new Book(id, "title");
    }
}
