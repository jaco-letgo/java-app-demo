package com.letgo.book.unit.application.find;

import com.letgo.book.application.find.FindBookQuery;
import com.letgo.book.application.find.FindBookQueryHandler;
import com.letgo.book.application.find.FindBookQueryResponse;
import com.letgo.book.domain.Book;
import com.letgo.book.domain.BookId;
import com.letgo.book.domain.BookNotFound;
import com.letgo.book.domain.BookRepository;
import com.letgo.book.infrastructure.persistence.InMemoryBookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FindBookQueryHandlerTest {
    private final BookRepository repository = new InMemoryBookRepository();
    private final FindBookQueryHandler handler = new FindBookQueryHandler(repository);

    @Test
    public void itShouldFindABook() {
        BookId bookId = BookId.create("12345");
        String title = "Whatever";
        repository.save(Book.create(bookId, title));

        FindBookQuery query = new FindBookQuery(bookId.value());
        FindBookQueryResponse response = handler.handle(query);
        assert response.title().equals(title);
    }

    @Test
    public void itShouldThrowAnExceptionWhenBookIsNotFound() {
        FindBookQuery query = new FindBookQuery(BookId.create("12345").value());

        BookNotFound exception = Assertions.assertThrowsExactly(
                BookNotFound.class,
                () -> handler.handle(query)
        );
        assert exception.getMessage().equals("Book not found with id 12345");
    }
}
