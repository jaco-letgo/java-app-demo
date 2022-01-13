package com.letgo.book.infrastructure.persistence.mapping;

import com.letgo.book.domain.BookId;
import com.letgo.book.domain.BookTitle;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "books")
final public class HibernateBookEntity {
    @Id
    @Type(type = "com.letgo.book.infrastructure.persistence.mapping.BookIdType")
    private BookId id;
    @Convert(converter = BookTitleConverter.class)
    @Column(nullable = false)
    private BookTitle title;

    public HibernateBookEntity() {
    }

    public HibernateBookEntity(BookId id, BookTitle title) {
        this.id = id;
        this.title = title;
    }

    public BookId id() {
        return id;
    }

    public BookTitle title() {
        return title;
    }
}
