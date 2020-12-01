package org.alessio29.pbtaBot.internal.redis

import org.alessio29.pbtaBot.internal.utils.fromJson
import org.alessio29.pbtaBot.internal.utils.toJson
import org.alessio29.pbtaBot.internal.utils.toJsonOrNull
import java.lang.RuntimeException

interface RedisSaveLoad {
    fun save(redisClient: RedisClient)
    fun load(redisClient: RedisClient)
}

interface DataStringConverter<T : Any> {
    fun dataToString(data: T): String
    fun stringToData(string: String): T
}

data class ChannelId(val guildId: String, val channelId: String)

object DefaultChannelIdConverter : DataStringConverter<ChannelId> {
    private const val DELIMITER = ':'

    override fun dataToString(data: ChannelId): String =
        "${data.guildId}$DELIMITER${data.channelId}"

    override fun stringToData(string: String): ChannelId {
        val parts = string.split(DELIMITER)
        if (parts.size != 2) {
            throw RuntimeException("Unexpected ChannelId encoding: '$string'")
        }
        return ChannelId(parts[0], parts[1])
    }
}

class JsonBasedConverter<T : Any>(
    private val valueClass: Class<T>
) : DataStringConverter<T> {

    override fun dataToString(data: T): String =
        data.toJson()

    override fun stringToData(string: String): T =
        string.fromJson(valueClass)
}

class ChannelDataStorage<T : Any>(
    private val redisKey: String,
    private val map: MutableMap<ChannelId, T>,
    private val channelIdConverter: DataStringConverter<ChannelId>,
    private val dataConverter: DataStringConverter<T>
) : MutableMap<ChannelId, T> by map, RedisSaveLoad {
    val data: Map<ChannelId, T> get() = map

    operator fun get(guildId: String, channelId: String) =
        get(ChannelId(guildId, channelId))

    operator fun set(guildId: String, channelId: String, value: T) {
        put(ChannelId(guildId, channelId), value)
    }

    override fun save(redisClient: RedisClient) {
        redisClient.saveMapAtKey(
            redisKey,
            entries.associateBy(
                { channelIdConverter.dataToString(it.key) },
                { dataConverter.dataToString(it.value) }
            )
        )
    }

    override fun load(redisClient: RedisClient) {
        map.clear()
        redisClient.loadMapAtKey(redisKey)
            .entries.associateByTo(
                map,
                { channelIdConverter.stringToData(it.key) },
                { dataConverter.stringToData(it.value) }
            )
    }

    companion object {
        fun <T : Any> classValueStorage(valueClass: Class<T>) =
            ChannelDataStorage(
                redisKey = valueClass.name,
                map = HashMap(),
                channelIdConverter = DefaultChannelIdConverter,
                dataConverter = JsonBasedConverter(valueClass)
            )
    }
}