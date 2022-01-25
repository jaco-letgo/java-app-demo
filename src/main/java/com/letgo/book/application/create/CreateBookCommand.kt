package com.letgo.book.application.create

import com.letgo.shared.application.bus.command.Command

data class CreateBookCommand(
    val id: String,
    val title: String
) : Command
