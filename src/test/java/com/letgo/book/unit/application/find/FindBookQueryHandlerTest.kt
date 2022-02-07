package com.letgo.book.unit.application.find;

import com.letgo.book.application.find.FindBookQuery;
import com.letgo.book.application.find.FindBookQueryHandler;
import com.letgo.book.application.find.FindBookQueryResponse;
import com.letgo.book.domain.Book;
import com.letgo.book.domain.BookFinder;
import com.letgo.book.domain.BookNotFound;
import com.letgo.book.unit.application.BookTestCase;
import com.letgo.book.unit.domain.BookIdMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

final public class FindBookQueryHandlerTest extends BookTestCase {
    private final FindBookQueryHandler handler = new FindBookQueryHandler(new BookFinder(repository));

    @Test
    public void itShouldFindABook() {
        Book book = anExistingBook();

        FindBookQuery query = new FindBookQuery(book.id().value());
        FindBookQueryResponse response = handler.handle(query);

        assertEquals(book.title().value(), response.getTitle());
    }

    @Test
    public void itShouldThrowAnExceptionWhenBookIsNotFound() {
        String id = BookIdMother.random().value();
        FindBookQuery query = new FindBookQuery(id);

        BookNotFound exception = assertThrowsExactly(
                BookNotFound.class,
                () -> handler.handle(query)
        );

        assertEquals("Book not found with id " + id, exception.getMessage());
    }
}
