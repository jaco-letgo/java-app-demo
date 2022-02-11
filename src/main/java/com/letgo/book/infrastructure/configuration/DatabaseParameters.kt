package com.letgo.book.infrastructure.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class DatabaseParameters(
    @Value("\${database.name}") private val databaseName: String,
    @Value("\${database.host}") private val databaseHost: String,
    @Value("\${database.port}") private val databasePort: String,
    @Value("\${database.user}") val databaseUser: String,
    @Value("\${database.password}") val databasePassword: String,
    val databaseUrl: String = "jdbc:mysql://${databaseHost}:${databasePort}/${databaseName}"
)
