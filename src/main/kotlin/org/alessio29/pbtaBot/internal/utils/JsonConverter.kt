package org.alessio29.pbtaBot.internal.utils

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper


object JsonConverter {
    private val mapper: ObjectMapper = ObjectMapper().apply { findAndRegisterModules() }

    fun <T> fromJson(json: String, clazz: Class<T>): T? {
        return try {
            mapper.readValue(json, clazz)
        } catch (e: JsonProcessingException) {
            println(e.toString())
            null
        }
    }

    fun toJson(o: Any): String? {
        return try {
            mapper.writeValueAsString(o)
        } catch (e: JsonProcessingException) {
            println(e.toString())
            null
        }
    }
}

fun Any.toJson() = JsonConverter.toJson(this)
fun <T> String.fromJson(clazz: Class<T>) = JsonConverter.fromJson(this, clazz)