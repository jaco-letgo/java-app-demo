package com.letgo.book.infrastructure.persistence.mapping

import com.letgo.book.domain.BookId
import org.hibernate.annotations.Type
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "books")
class HibernateBookEntity {
    @Id
    @Type(type = "com.letgo.book.infrastructure.persistence.mapping.BookIdType")
    private var id: BookId? = null

    @Embedded
    private var title: BookTitleEmbeddable? = null

    constructor()
    constructor(id: BookId, title: BookTitleEmbeddable) {
        this.id = id
        this.title = title
    }

    fun id(): BookId? {
        return id
    }

    fun title(): BookTitleEmbeddable? {
        return title
    }
}
