package com.letgo.book.application.changeTitle

import com.letgo.shared.application.bus.command.Command
import java.time.LocalDateTime

class ChangeTitleCommand : Command {
    private val id: String
    private val newTitle: String
    private val occurredOn: LocalDateTime

    constructor(id: String, newTitle: String, occurredOn: LocalDateTime) {
        this.id = id
        this.newTitle = newTitle
        this.occurredOn = occurredOn
    }

    constructor(id: String, newTitle: String) {
        this.id = id
        this.newTitle = newTitle
        occurredOn = LocalDateTime.now()
    }

    fun id(): String {
        return id
    }

    fun newTitle(): String {
        return newTitle
    }

    fun occurredOn(): LocalDateTime {
        return occurredOn
    }
}
