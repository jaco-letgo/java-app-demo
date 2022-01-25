package com.letgo.book.infrastructure.persistence.mapping

import com.letgo.book.domain.Book
import com.letgo.book.domain.BookTitle

object HibernateBookMapper {
    fun toDomainEntity(entity: HibernateBookEntity): Book {
        val book: Book = Book.create(
            entity.id(), BookTitle.create(
                entity.title()!!.value(), entity.title()!!.createdAt()
            )
        )
        book.retrieveEvents()
        return book
    }

    fun toOrmEntity(entity: Book): HibernateBookEntity {
        return HibernateBookEntity(entity.id(), BookTitleEmbeddable(entity.title()))
    }
}
