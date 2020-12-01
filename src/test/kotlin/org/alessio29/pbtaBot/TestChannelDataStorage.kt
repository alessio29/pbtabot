package org.alessio29.pbtaBot

import org.alessio29.pbtaBot.internal.redis.ChannelDataStorage
import org.alessio29.pbtaBot.internal.redis.RedisClient
import org.junit.Assert
import org.junit.Test

class TestChannelDataStorage {
    data class MyData(
        val int: Int,
        val string: String,
        val xy: XY
    ) {
        // For Jackson
        constructor() : this(0, "", XY())
    }

    data class XY(
        val x: Int,
        val y: Int
    ) {
        // For Jackson
        constructor() : this(0, 0)
    }

    @Test
    fun testSaveLoadSome() {
        val redisClient = RedisClient()

        val myDataSaved = ChannelDataStorage.classValueStorage(MyData::class.java)
        myDataSaved["g1", "c1"] = MyData(1, "abc", XY(2, 3))
        myDataSaved["g2", "c2"] = MyData(10, "def", XY(20, 30))

        val xySaved = ChannelDataStorage.classValueStorage(XY::class.java)
        xySaved["g1", "c1"] = XY(1, 1)
        xySaved["g2", "c3"] = XY(2, 3)
        xySaved["g2", "c4"] = XY(2, 4)

        myDataSaved.save(redisClient)
        xySaved.save(redisClient)

        val myDataLoaded = ChannelDataStorage.classValueStorage(MyData::class.java)
        myDataLoaded.load(redisClient)
        Assert.assertEquals(myDataSaved.data, myDataLoaded.data)

        val xyLoaded = ChannelDataStorage.classValueStorage(XY::class.java)
        xyLoaded.load(redisClient)
        Assert.assertEquals(xySaved.data, xyLoaded.data)
    }
}