package com.letgo.book.domain

import com.letgo.shared.domain.AggregateRoot

class Book private constructor(
    private val id: BookId,
    private var status: BookStatus,
    private var title: BookTitle,
) : AggregateRoot() {
    constructor(id: String, title: String, createdAt: String) : this(BookId(id), BookTitle(title, createdAt))
    constructor(id: BookId, title: BookTitle) : this(id, BookStatus.Created, title)

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
}
