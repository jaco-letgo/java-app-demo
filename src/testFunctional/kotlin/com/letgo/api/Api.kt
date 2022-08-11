package com.letgo.api

interface Api<T> {
    fun get(endpoint: String, headers: Map<String, String>, queryParams: Map<String, String>): T
    fun post(endpoint: String, headers: Map<String, String>, body: String): T
    fun put(endpoint: String, headers: Map<String, String>, body: String): T
    fun delete(endpoint: String, headers: Map<String, String>, body: String): T
}
