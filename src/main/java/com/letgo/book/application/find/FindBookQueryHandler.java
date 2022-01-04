package com.letgo.book.application.find;

import com.letgo.book.domain.Book;
import com.letgo.book.domain.BookFinder;
import com.letgo.book.domain.BookId;
import com.letgo.shared.application.bus.query.QueryHandler;
import org.springframework.stereotype.Service;

@Service
public class FindBookQueryHandler implements QueryHandler<FindBookQuery> {
    private final BookFinder finder;

    public FindBookQueryHandler(BookFinder finder) {
        this.finder = finder;
    }

    public FindBookQueryResponse handle(FindBookQuery query) {
        Book book = finder.find(BookId.create(query.id()));
        return new FindBookQueryResponse(book.title());
    }
}
