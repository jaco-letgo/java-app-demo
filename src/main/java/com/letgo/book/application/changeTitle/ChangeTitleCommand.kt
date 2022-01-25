package com.letgo.book.application.changeTitle

import com.letgo.shared.application.bus.command.Command
import java.time.LocalDateTime

class ChangeTitleCommand(
    val id: String,
    val newTitle: String,
    val occurredOn: LocalDateTime
) : Command
