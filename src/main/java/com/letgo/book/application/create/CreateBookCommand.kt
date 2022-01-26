package com.letgo.book.application.create

import com.letgo.shared.application.bus.command.Command
import java.time.LocalDateTime

class CreateBookCommand(
    val id: String,
    val title: String,
    val titleCreatedAt: LocalDateTime
) : Command
