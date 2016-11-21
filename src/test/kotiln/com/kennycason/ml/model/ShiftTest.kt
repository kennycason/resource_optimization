package com.kennycason.ml.model

import com.kennycason.ml.model.time.Range
import com.kennycason.ml.model.time.Shift
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Created by kenny on 11/20/16.
 */
class ShiftTest {

    @Test
    fun assign() {
        val shift = Shift(shift1 = Range(startHour = 7.0, endHour = 11.0), shift2 = Range(startHour = 11.5, endHour = 16.0))

        // all out of range
        assertFalse(shift.assignIfPossible(Range(startHour = 6.5, endHour = 8.0)).success)
        assertFalse(shift.assignIfPossible(Range(startHour = 10.5, endHour = 11.5)).success)
        assertFalse(shift.assignIfPossible(Range(startHour = 11.0, endHour = 11.5)).success)
        assertFalse(shift.assignIfPossible(Range(startHour = 15.5, endHour = 16.5)).success)

        // assign first hour
        assertTrue(shift.assignIfPossible(Range(startHour = 7.0, endHour = 8.0)).success)
        // can't assign twice
        assertFalse(shift.assignIfPossible(Range(startHour = 7.0, endHour = 8.0)).success)
        // can't assign overlapping
        assertFalse(shift.assignIfPossible(Range(startHour = 7.5, endHour = 8.5)).success)
        // should be able to assign next hour block
        assertTrue(shift.assignIfPossible(Range(startHour = 8.0, endHour = 9.0)).success)
        // assigned bits for 14, 15, 16, 17 should be set.
        println(shift)

        // assign first gap, available for 2 hours (should be 9-11 (i.e. assigned units 18 - 21)
        assertTrue(shift.assignIfPossible(2.0).success)
        println(shift)

        // assign first gap, available for 2 hours (should be 11.5-13.5 (i.e. assigned units 23 - 26)
        assertTrue(shift.assignIfPossible(2.0).success)
        println(shift)
    }
}