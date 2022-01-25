package com.letgo.book.infrastructure.persistence.mapping;

import com.letgo.book.domain.BookId;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "books")
final public class HibernateBookEntity {
    @Id
    @Type(type = "com.letgo.book.infrastructure.persistence.mapping.BookIdType")
    private BookId id;
    @Embedded
    private BookTitleEmbeddable title;

    public HibernateBookEntity() {
    }

    public HibernateBookEntity(BookId id, BookTitleEmbeddable title) {
        this.id = id;
        this.title = title;
    }

    public BookId id() {
        return id;
    }

    public BookTitleEmbeddable title() {
        return title;
    }
}
