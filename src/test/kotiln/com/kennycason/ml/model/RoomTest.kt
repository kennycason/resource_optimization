package com.kennycason.ml.model

import com.kennycason.ml.model.time.Range
import org.eclipse.collections.impl.factory.Lists
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Created by kenny on 11/20/16.
 */
class RoomTest {
    @Test
    fun assign() {
        val service = Service(name = "Basic Massage", possibleDurations = Lists.immutable.of(1.0))
        val room = Room(
                name = "Ultimate Room",
                services = Lists.immutable.of(service))

        val assignment = Range(8.0, 10.0)
        assertTrue(room.assignIfPossible(service, assignment).success)
        assertFalse(room.assignIfPossible(service, assignment).success)
    }
}