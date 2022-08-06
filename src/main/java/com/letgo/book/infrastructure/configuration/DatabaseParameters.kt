package com.letgo.book.infrastructure.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "database")
data class DatabaseParameters(
    val name: String,
    val host: String,
    val port: String,
    val user: String,
    val password: String,
)
