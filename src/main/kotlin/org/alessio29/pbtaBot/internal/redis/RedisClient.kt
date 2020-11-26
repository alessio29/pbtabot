package org.alessio29.pbtaBot.internal.redis


import org.alessio29.pbtaBot.internal.utils.JsonConverter
import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.apache.log4j.Logger
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool


object RedisClient {
    private const val defaultHost = "localhost"
    private const val defaultPort = 6379
    private const val TIMEOUT = 3000
    private var jedisPool: JedisPool? = null
    private var host: String? = null
    private var port = 0
    private var pass: String? = null
    const val DELIMITER = ":"
    private val log: Logger = Logger.getLogger(RedisClient::class.java)

    private val client: Jedis
        private get() {
            if (jedisPool == null) {
                val jedisConfig = GenericObjectPoolConfig<Jedis>();

                jedisConfig.setMaxTotal(3)
                jedisConfig.setMaxIdle(3)
                host = if (host == null || host!!.trim { it <= ' ' }.isEmpty()) defaultHost else host
                port = if (port == 0) defaultPort else port
                jedisPool = JedisPool(jedisConfig, host, port, TIMEOUT, pass)
            }
            return jedisPool!!.getResource()
        }

    fun setup(redisHost: String?, redisPort: Int, redisPass: String?) {
        host = redisHost
        port = redisPort
        pass = redisPass
    }

    fun saveMapAtKey(key: String, map: Map<*, *>) {
        try {
            client.use{ client -> client.hmset(key, map) }
        } catch (e: Exception) {
            log.debug("Error while saving map to Redis storage.", e)
        }
    }

    fun remove(key: String?, fieldKey: String?) {
        try {
            client.use({ client -> client.hdel(key, fieldKey) })
        } catch (e: Exception) {
            log.debug("Error while deleting value from Redis storage.", e)
        }
    }

    fun loadMapAtKey(key: String?): MutableMap<String, String> {
        try {
            client.use({ client -> return client.hgetAll(key) })
        } catch (e: Exception) {
            log.debug("Error while loading value from Redis storage.", e)
            return mutableMapOf()
        }
    }

    fun asJson(o: Any?): String? {
        return JsonConverter.instance.toJson(o)
    }
}

private fun Jedis.hmset(key: String, map: Map<*, *>) {
    this.hmset(key,map)
}
