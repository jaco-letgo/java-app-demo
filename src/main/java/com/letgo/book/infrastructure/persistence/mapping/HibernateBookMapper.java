package com.letgo.book.infrastructure.persistence.mapping;

import com.letgo.book.domain.Book;
import com.letgo.book.domain.BookTitle;

final public class HibernateBookMapper {
    public static Book toDomainEntity(HibernateBookEntity entity) {
        Book book = Book.create(entity.id(), BookTitle.create(entity.title().value(), entity.title().createdAt()));
        book.retrieveEvents();
        return book;
    }

    public static HibernateBookEntity toOrmEntity(Book entity) {
        return new HibernateBookEntity(entity.id(), new BookTitleEmbeddable(entity.title()));
    }
}
