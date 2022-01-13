package com.letgo.book.infrastructure.persistence.mapping;

import com.letgo.book.domain.Book;

final public class HibernateBookMapper {
    public static Book toDomainEntity(HibernateBookEntity entity) {
        return Book.create(entity.id(), entity.title());
    }

    public static HibernateBookEntity toOrmEntity(Book entity) {
        return new HibernateBookEntity(entity.id(), entity.title());
    }
}
