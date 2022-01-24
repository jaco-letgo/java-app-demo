package com.letgo.book.application.create

import com.letgo.shared.application.bus.command.Command

class CreateBookCommand(private val id: String, private val title: String) : Command {
    fun id(): String {
        return id
    }

    fun title(): String {
        return title
    }
}
