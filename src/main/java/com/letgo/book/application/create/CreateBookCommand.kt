package com.letgo.book.application.create

import com.letgo.shared.application.bus.command.Command

class CreateBookCommand(
    val id: String,
    val title: String,
    val titleCreatedAt: String
) : Command
