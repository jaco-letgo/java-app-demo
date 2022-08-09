package com.letgo.shared.infrastructure.bus.serialize

interface MessageSerializer<T> {
    fun serialize(message: T): String
    fun deserialize(message: String): T
}
