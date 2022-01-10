package com.letgo.book.unit.application.find;

import com.letgo.book.application.find.FindBookQuery;
import com.letgo.book.application.find.FindBookQueryHandler;
import com.letgo.book.application.find.FindBookQueryResponse;
import com.letgo.book.domain.*;
import com.letgo.book.infrastructure.persistence.InMemoryBookRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FindBookQueryHandlerTest {
    private final BookRepository repository = new InMemoryBookRepository();
    private final FindBookQueryHandler handler = new FindBookQueryHandler(new BookFinder(repository));

    @Test
    public void itShouldFindABook() {
        BookId bookId = BookId.create();
        String title = "Whatever";
        repository.save(Book.create(bookId, title));

        FindBookQuery query = new FindBookQuery(bookId.value());
        FindBookQueryResponse response = handler.handle(query);

        assertEquals(title, response.title());
    }

    @Test
    public void itShouldThrowAnExceptionWhenBookIsNotFound() {
        BookId bookId = BookId.create();
        FindBookQuery query = new FindBookQuery(bookId.value());

        BookNotFound exception = assertThrowsExactly(
                BookNotFound.class,
                () -> handler.handle(query)
        );

        assertEquals("Book not found with id " + bookId.value(), exception.getMessage());
    }
}
