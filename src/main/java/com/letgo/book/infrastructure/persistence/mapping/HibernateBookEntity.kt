package com.letgo.book.infrastructure.persistence.mapping

import com.letgo.book.domain.BookId
import com.letgo.book.domain.BookStatus
import org.hibernate.annotations.Type
import javax.persistence.*

@Entity
@Table(name = "books")
class HibernateBookEntity {
    @Id
    @Type(type = "com.letgo.book.infrastructure.persistence.mapping.BookIdType")
    private var id: BookId? = null

    @Embedded
    private var title: BookTitleEmbeddable? = null

    @Enumerated(EnumType.ORDINAL)
    private var status: BookStatus? = null

    constructor()
    constructor(id: BookId, title: BookTitleEmbeddable, status: BookStatus) {
        this.id = id
        this.title = title
        this.status = status
    }

    fun id(): BookId? {
        return id
    }

    fun title(): BookTitleEmbeddable? {
        return title
    }

    fun status(): BookStatus? {
        return status
    }
}
