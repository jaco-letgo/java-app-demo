package com.letgo.book.infrastructure.persistence.mapping;

import com.letgo.book.domain.BookTitle;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
final public class BookTitleEmbeddable {
    @Column(name = "title", nullable = false)
    private String value;
    @Column(name = "title_updated_at", nullable = false)
    private LocalDateTime createdAt;

    public BookTitleEmbeddable() {
    }

    public BookTitleEmbeddable(BookTitle title) {
        this.value = title.value();
        this.createdAt = title.createdAt();
    }

    public String value() {
        return value;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }
}
