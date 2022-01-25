package com.letgo.shared.application.bus.command

interface CommandBus {
    @Throws(Exception::class)
    fun dispatch(command: Command)
}
