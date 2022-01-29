package com.letgo.shared.application.bus.command

interface CommandBus {
    @Throws(RuntimeException::class)
    fun dispatch(command: Command)
}
