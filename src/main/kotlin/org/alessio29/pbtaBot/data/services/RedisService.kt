package org.alessio29.pbtaBot.data.services

import org.alessio29.pbtaBot.data.model.*
import org.alessio29.pbtaBot.internal.redis.RedisClient
import org.alessio29.pbtaBot.internal.utils.fromJsonOrNull
import org.alessio29.pbtaBot.internal.utils.toJsonOrNull

class RedisService(private val redis: RedisClient) {

    private val storage: MutableMap<String, MutableMap<String, NamedEntity>> = mutableMapOf()
    private val HACKS_KEY = "HACKS"
    private val TAGS_KEY = "TAGS"
    private val MOVES_KEY = "MOVES"
    private val PLAYBOOK_KEY = "PLAYBOOKS"
    private val STATS_KEY = "STATS"

    private val classIndex = mutableMapOf(
        TAGS_KEY to Tag::class.java,
        HACKS_KEY to Hack::class.java,
        MOVES_KEY to Move::class.java,
        PLAYBOOK_KEY to Playbook::class.java,
        STATS_KEY to Stat::class.java
    )

    init {
        loadAll()
    }

    fun add(value: NamedEntity) {
        val storageKey = when (value) {
            is Tag -> TAGS_KEY
            is Hack -> HACKS_KEY
            is Stat -> STATS_KEY
            is Move -> MOVES_KEY
            is Playbook -> PLAYBOOK_KEY
            else -> return
        }
        val subStorage = storage.getOrPut(storageKey) { mutableMapOf() }
        subStorage[value.name] = value
    }

    fun find(tag: String, name: String) =
        storage[tag]?.get(name)

    fun remove(tag: String, name: String) {
        storage[tag]?.remove(name)
        saveAll()
    }

    fun saveAll() {
        for (tagEntry in storage) {
            val tagKey = tagEntry.key
            val converted: MutableMap<String, String> = mutableMapOf()
            for (entry in tagEntry.value) {
                converted[entry.key] = entry.value.toJsonOrNull() ?: continue
            }
            redis.saveMapAtKey(tagKey, converted)
        }
    }

    private fun loadAll() {

        for ((tag, clazz) in classIndex) {
            val tagStorage = storage.getOrPut(tag) { mutableMapOf() }
            val map = redis.loadMapAtKey(tag)
            for (value in map.values) {
                val parsed = value.fromJsonOrNull(clazz) ?: continue
                tagStorage[parsed.name] = parsed
            }
        }
    }
}