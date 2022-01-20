package com.letgo.book.infrastructure.persistence.mapping;

import com.letgo.book.domain.Book;

final public class HibernateBookMapper {
    public static Book toDomainEntity(HibernateBookEntity entity) {
        Book book = Book.create(entity.id(), entity.title());
        book.retrieveEvents();
        return book;
    }

    public static HibernateBookEntity toOrmEntity(Book entity) {
        return new HibernateBookEntity(entity.id(), entity.title());
    }
}
