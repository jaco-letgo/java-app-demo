package com.letgo.book.application.changeTitle

import com.letgo.shared.application.bus.command.Command

class ChangeTitleCommand(
    val id: String,
    val newTitle: String,
    val occurredOn: String
) : Command
