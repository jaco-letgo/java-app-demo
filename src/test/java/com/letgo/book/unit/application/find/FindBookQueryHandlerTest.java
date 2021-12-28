package com.letgo.book.unit.application.find;

import com.letgo.book.application.find.FindBookQuery;
import com.letgo.book.application.find.FindBookQueryHandler;
import com.letgo.book.domain.BookRepository;
import com.letgo.book.infrastructure.persistence.InMemoryBookRepository;
import org.junit.jupiter.api.Test;

public class FindBookQueryHandlerTest {
    private final BookRepository repository = new InMemoryBookRepository();
    private final FindBookQueryHandler handler = new FindBookQueryHandler(repository);

    @Test
    public void itShouldHandleQuery() {
        assert handler.handle(new FindBookQuery("123")).title().equals("title");
    }
}
