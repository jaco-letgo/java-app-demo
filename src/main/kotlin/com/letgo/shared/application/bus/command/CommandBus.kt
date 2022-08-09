package com.letgo.shared.application.bus.command

interface CommandBus {
    fun dispatch(command: Command)
}
