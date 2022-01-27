package com.letgo.book.domain

import com.letgo.shared.domain.AggregateRoot

class Book private constructor(
    private val id: BookId,
    private var status: BookStatus,
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

    fun hasBeenEdited(): Boolean {
        return status == BookStatus.Edited
    }

    fun canChangeTitle(newTitle: BookTitle): Boolean {
        return title != newTitle && newTitle.isNewerThan(title)
    }

    fun changeTitle(newTitle: BookTitle) {
        storeEvent(BookTitleChanged(id.value(), title.value(), newTitle.value(), newTitle.createdAt()))
        title = newTitle
        status = BookStatus.Edited
    }

    companion object {
        fun create(id: String, title: String, createdAt: String): Book {
            return create(BookId.create(id), BookTitle.create(title, createdAt))
        }

        fun create(id: BookId, title: BookTitle): Book {
            return Book(id, BookStatus.Created, title)
        }
    }
}
