package com.letgo.shared.infrastructure.bus.command

import com.letgo.shared.application.bus.command.Command

internal data class ACommand(val parameter: String = "whatever") : Command
