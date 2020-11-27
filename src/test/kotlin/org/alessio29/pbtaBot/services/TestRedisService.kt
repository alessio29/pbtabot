package org.alessio29.pbtaBot.services

import org.alessio29.pbtaBot.data.model.Tag
import org.alessio29.pbtaBot.data.services.RedisService
import org.alessio29.pbtaBot.internal.redis.RedisClient
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TestRedisService {

    var redisService: RedisService? = null

    @Before
    fun setup() {
        val redisClient = RedisClient()
        redisService = RedisService(redisClient)
    }

    @After
    fun destroy() {
        redisService?.saveAll()
        redisService = null
    }

    @Test
    fun test1() {
        Assert.assertNotNull("Redis service not started.", redisService)
        val tagExpected = Tag("testTagName", "Tag description")
        redisService?.add(tagExpected)
        var tagActual = redisService?.find("TAGS", "testTagName")
        Assert.assertEquals(
            tagExpected,
            tagActual
        )
    }

    @Test
    fun testRemove() {
        val tagExpected = Tag("testTag2Remove", "Tag description 1")
        var tagActual =redisService?.find("TAGS", "testTag2Remove")
        Assert.assertNull("Tag must be null!" ,tagActual)
        redisService?.add(tagExpected)
        tagActual =redisService?.find("TAGS", "testTag2Remove")
        Assert.assertEquals(tagExpected, tagActual)
        redisService?.remove("TAGS", "testTag2Remove")
        tagActual = redisService?.find("TAGS", "testTag2Remove")
        Assert.assertNull("Tag must be null!" ,tagActual)
    }
}