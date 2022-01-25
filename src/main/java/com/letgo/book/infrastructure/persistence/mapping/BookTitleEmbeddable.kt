package com.letgo.book.infrastructure.persistence.mapping

import com.letgo.book.domain.BookTitle
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class BookTitleEmbeddable {
    @Column(name = "title", nullable = false)
    private var value: String? = null

    @Column(name = "title_updated_at", nullable = false)
    private var createdAt: LocalDateTime? = null

    constructor()
    constructor(title: BookTitle) {
        value = title.value()
        createdAt = title.createdAt()
    }

    fun value(): String? {
        return value
    }

    fun createdAt(): LocalDateTime? {
        return createdAt
    }
}
