package org.alessio29.pbtaBot.internal.utils

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper

object JsonMapper : ObjectMapper() {
    init {
        findAndRegisterModules()
    }
}

fun Any.toJson() =
    JsonMapper.writeValueAsString(this)

fun Any.toJsonOrNull() =
    try {
        JsonMapper.writeValueAsString(this)
    } catch (e: JsonProcessingException) {
        println(e.toString())
        null
    }

fun <T : Any> String.fromJson(clazz: Class<T>) =
    JsonMapper.readValue(this, clazz)

fun <T : Any> String.fromJsonOrNull(clazz: Class<T>) =
    try {
        JsonMapper.readValue(this, clazz)
    } catch (e: JsonProcessingException) {
        println(e.toString())
        null
    }