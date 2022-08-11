package com.letgo.context

import io.cucumber.java.Before

class Context {
    private lateinit var context: MutableMap<String, Any?>

    @Before
    fun context() {
        context = mutableMapOf()
    }

    fun contains(key: String): Boolean = context.contains(key)

    fun set(key: String, value: Any?) {
        context[key] = value
    }

    fun getRaw(key: String): Any? = context[key]

    inline fun <reified T : Any> get(key: String): T? = getRaw(key)?.let { it as T }
}
