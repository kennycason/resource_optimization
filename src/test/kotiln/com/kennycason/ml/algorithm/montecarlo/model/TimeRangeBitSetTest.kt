package com.kennycason.ml.algorithm.montecarlo.model

import com.kennycason.ml.model.time.Range
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Created by kenny on 11/20/16.
 */
class TimeRangeBitSetTest {
    @Test
    fun basic() {
        val timeRangeBitSet = TimeRangeBitSet()

        val range = Range(startHour = 6.0, endHour = 8.0)
        val fromRange = TimeRangeBitSet.Builder.from(range)

        val newTimeRangeBitSet = timeRangeBitSet.or(fromRange)
        // this is a new object
        assertFalse(newTimeRangeBitSet === timeRangeBitSet)
        assertTrue(newTimeRangeBitSet.get(12))
        assertTrue(newTimeRangeBitSet.get(13))
        assertTrue(newTimeRangeBitSet.get(14))
        assertTrue(newTimeRangeBitSet.get(15))
        assertFalse(timeRangeBitSet.get(12))
        assertFalse(timeRangeBitSet.get(13))
        assertFalse(timeRangeBitSet.get(14))
        assertFalse(timeRangeBitSet.get(15))

        // in place and
        timeRangeBitSet.orInPlace(fromRange)
        assertTrue(timeRangeBitSet.get(12))
        assertTrue(timeRangeBitSet.get(13))
        assertTrue(timeRangeBitSet.get(14))
        assertTrue(timeRangeBitSet.get(15))
    }

    @Test
    fun contains() {
        val timeRangeBitSet = TimeRangeBitSet()
        timeRangeBitSet.set(TimeRangeBitSet.Builder.from(Range(8.0, 12.0)))

        assertTrue(timeRangeBitSet.contains(TimeRangeBitSet.Builder.from(Range(8.0, 12.0))))
        assertTrue(timeRangeBitSet.contains(TimeRangeBitSet.Builder.from(Range(9.0, 11.0))))

        assertFalse(timeRangeBitSet.contains(TimeRangeBitSet.Builder.from(Range(7.0, 9.0))))
        assertFalse(timeRangeBitSet.contains(TimeRangeBitSet.Builder.from(Range(11.0, 13.0))))
    }

    @Test
    fun overlaps() {
        val timeRangeBitSet = TimeRangeBitSet()
        timeRangeBitSet.set(TimeRangeBitSet.Builder.from(Range(8.0, 12.0)))

        assertTrue(timeRangeBitSet.overlaps(TimeRangeBitSet.Builder.from(Range(8.0, 12.0))))
        assertTrue(timeRangeBitSet.overlaps(TimeRangeBitSet.Builder.from(Range(9.0, 11.0))))
        assertTrue(timeRangeBitSet.overlaps(TimeRangeBitSet.Builder.from(Range(7.0, 9.0))))
        assertTrue(timeRangeBitSet.overlaps(TimeRangeBitSet.Builder.from(Range(11.0, 13.0))))

        assertFalse(timeRangeBitSet.overlaps(TimeRangeBitSet.Builder.from(Range(6.0, 7.0))))
        assertFalse(timeRangeBitSet.overlaps(TimeRangeBitSet.Builder.from(Range(12.5, 13.0))))
    }

    @Test
    fun setUnset() {
        val timeRangeBitSet = TimeRangeBitSet()
        timeRangeBitSet.set(16) // 8.0
        timeRangeBitSet.set(17)
        timeRangeBitSet.set(18)
        timeRangeBitSet.set(19) // 9.5

        // single field
        assertTrue(timeRangeBitSet.get(16))
        assertTrue(timeRangeBitSet.get(17))
        assertTrue(timeRangeBitSet.get(18))
        assertTrue(timeRangeBitSet.get(19))
        timeRangeBitSet.unset(19)
        assertFalse(timeRangeBitSet.get(19))
        timeRangeBitSet.set(19)

        val range = Range(8.0, 10.0)
        timeRangeBitSet.unset(TimeRangeBitSet.Builder.from(range))
        assertFalse(timeRangeBitSet.get(16))
        assertFalse(timeRangeBitSet.get(17))
        assertFalse(timeRangeBitSet.get(18))
        assertFalse(timeRangeBitSet.get(19))
    }

}