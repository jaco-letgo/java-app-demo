package com.letgo.book.domain

import com.letgo.shared.domain.DomainEvent

class BookTitleChanged(
    id: String,
    private val oldTitle: String,
    private val newTitle: String
) : DomainEvent(id) {
    fun oldTitle(): String {
        return oldTitle
    }

    fun newTitle(): String {
        return newTitle
    }

    override fun name(): String {
        return "BookTitleChanged"
    }

    override fun body(): String {
        return "{'oldTitle': $oldTitle, 'newTitle': $newTitle}"
    }
}
