package com.letgo.shared.infrastructure.bus.serialize

import com.letgo.shared.application.bus.command.Command
import kotlin.reflect.KClass

interface CommandClassFinder {
    fun find(type: String): KClass<out Command>
}
