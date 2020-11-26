package org.alessio29.pbtaBot.internal.utils

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper


class JsonConverter private constructor() {
    fun <T> fromJson(json: String?, clazz: Class<T>?): T? {
        return try {
            mapper.readValue(json, clazz)
        } catch (e: JsonProcessingException) {
            println(e.toString())
            null
        }
    }

    fun toJson(o: Any?): String? {
        return try {
            mapper.writeValueAsString(o)
        } catch (e: JsonProcessingException) {
            println(e.toString())
            null
        }
    }

    companion object {
        val instance = JsonConverter()
        private val mapper: ObjectMapper = ObjectMapper()
    }
}