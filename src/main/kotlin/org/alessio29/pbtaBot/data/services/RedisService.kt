package org.alessio29.pbtaBot.data.services

import org.alessio29.pbtaBot.data.model.*
import org.alessio29.pbtaBot.internal.redis.RedisClient
import org.alessio29.pbtaBot.internal.utils.JsonConverter

class RedisService(private val redis: RedisClient) {

    private val storage: MutableMap<String, MutableMap<String, NamedEntity>> = mutableMapOf()
    private val HACKS_KEY = "HACKS"
    private val TAGS_KEY = "TAGS"
    private val MOVES_KEY = "MOVES"
    private val PLAYBOOK_KEY = "PLAYBOOKS"
    private val STATS_KEY = "STATS"

    private val classIndex: MutableMap<String, Class<out NamedEntity>> = mutableMapOf(
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
        val subStorage: MutableMap<String, NamedEntity> =
            when (value) {
                is Tag -> storage[TAGS_KEY]
                is Hack -> storage[HACKS_KEY]
                is Stat -> storage[STATS_KEY]
                is Move -> storage[MOVES_KEY]
                is Playbook -> storage[PLAYBOOK_KEY]
                else -> null
            } ?: return
        subStorage[value.name] = value
    }

    fun get(tag: String, name: String) =
        storage[tag]?.get(name)

    fun saveAll() {
        for (tagEntry in storage) {
            val tagKey : String = tagEntry.key
            val converted : MutableMap<String, String> = mutableMapOf()
            for (entry in tagEntry.value) {
                converted[entry.key] = JsonConverter.instance.toJson(entry.value)?:continue
            }
            redis.saveMapAtKey(tagKey, converted )
        }
    }

    private fun loadAll() {
        for (entry in classIndex) {
            val tag = entry.key
            val clazz = entry.value
            val tagStorage = storage[tag]?:mutableMapOf()
            val map: MutableMap<String, String> = redis.loadMapAtKey(tag)
            for (loaded in map) {
                val parsed = JsonConverter.instance.fromJson(loaded.value, clazz)
                if (parsed != null) {
                    tagStorage[parsed.name] = parsed
                }
            }
            storage[tag]=tagStorage
        }
    }
}