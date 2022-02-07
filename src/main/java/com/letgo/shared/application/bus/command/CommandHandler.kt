package com.letgo.shared.application.bus.command

interface CommandHandler<T : Command> {
    fun handle(command: T)
}
