package org.alessio29.pbtaBot.internal.redis

import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.apache.log4j.Logger
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool


object RedisClient {
    private const val defaultHost = "localhost"
    private const val defaultPort = 6379
    private const val TIMEOUT = 3000

    private var host: String? = null
    private var port = 0
    private var pass: String? = null
    const val DELIMITER = ":"
    private val log: Logger = Logger.getLogger(RedisClient::class.java)

    private val jedisPool: JedisPool by lazy {
        val jedisConfig = GenericObjectPoolConfig<Jedis>()
        jedisConfig.maxTotal = 3
        jedisConfig.maxIdle = 3
        host = if (host == null || host!!.trim { it <= ' ' }.isEmpty()) defaultHost else host
        port = if (port == 0) defaultPort else port
        JedisPool(jedisConfig, host, port, TIMEOUT, pass)
    }

    private val client: Jedis
        get() = jedisPool.resource


    object Redis {
        private var host: String? = null
        private var port = 0
        private var pass: String? = null

        fun setup(/* bot configuration */) { ... }

        val client by lazy { RedisClient(...) }
    }

    class RedisClient(private val jedis: Jedis) {

        fun setup(redisHost: String?, redisPort: Int, redisPass: String?) {
            host = redisHost
            port = redisPort
            pass = redisPass
        }

        fun saveMapAtKey(key: String, map: Map<*, *>) {
            try {
                client.use { client -> client.hmset(key, map) }
            } catch (e: Exception) {
                log.debug("Error while saving map to Redis storage.", e)
            }
        }

        fun remove(key: String?, fieldKey: String?) {
            try {
                client.use { client -> client.hdel(key, fieldKey) }
            } catch (e: Exception) {
                log.debug("Error while deleting value from Redis storage.", e)
            }
        }

        fun loadMapAtKey(key: String?): MutableMap<String, String> =
            try {
                client.use { client -> client.hgetAll(key) }
            } catch (e: Exception) {
                log.debug("Error while loading value from Redis storage.", e)
                mutableMapOf()
            }

        private fun Jedis.hmset(key: String, map: Map<*, *>) {
            this.hmset(key, map)
        }

    }



}


