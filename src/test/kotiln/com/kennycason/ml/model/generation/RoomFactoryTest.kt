package com.kennycason.ml.model.generation

import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by kenny on 11/8/16.
 */
class RoomFactoryTest {

    @Test
    fun roomGeneration() {
        val roomFactory = RoomFactory()
        val rooms = roomFactory.build(5)

        assertEquals(5, rooms.size())
        rooms.forEach(::println)
    }
}