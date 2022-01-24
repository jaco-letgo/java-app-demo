package com.letgo.shared.application.bus.command

import java.lang.Exception
import kotlin.Throws

interface CommandHandler<T : Command> {
    @Throws(Exception::class)
    fun handle(command: T)
}
