package com.letgo.book.domain

import com.letgo.shared.domain.AggregateRoot

class Book private constructor(
    private val id: BookId,
    private var title: BookTitle
) : AggregateRoot() {
    init {
        storeEvent(BookCreated(id.value(), title.value(), title.createdAt()))
    }

    fun id(): BookId {
        return id
    }

    fun title(): BookTitle {
        return title
    }

    fun canChangeTitle(newTitle: BookTitle): Boolean {
        return title != newTitle && newTitle.isNewerThan(title)
    }

    fun changeTitle(newTitle: BookTitle) {
        storeEvent(BookTitleChanged(id.value(), title.value(), newTitle.value(), newTitle.createdAt()))
        title = newTitle
    }

    companion object {
        @JvmStatic
        fun create(id: String, title: String, createdAt: String): Book {
            return Book(BookId.create(id), BookTitle.create(title, createdAt))
        }

        @JvmStatic
        fun create(id: BookId, title: BookTitle): Book {
            return Book(id, title)
        }
    }
}
