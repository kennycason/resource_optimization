package com.kennycason.ml.algorithm.montecarlo.model

import com.kennycason.ml.model.time.Range
import com.sun.org.apache.xpath.internal.operations.Bool
import java.util.*

/**
 * Created by kenny on 11/20/16.
 *
 * A class to provide immutable / mutable behavior as needed specific to this algorithm.
 *
 * This will replace most of the clanky range code
 */
class TimeRangeBitSet(private val bitset: BitSet = BitSet()) {

    // mutable
    fun set(i: Int) = bitset.set(i)

    fun set(timeRangeBitSet: TimeRangeBitSet) {
        (0.. timeRangeBitSet.bitset.length()).forEach { i ->
            if (timeRangeBitSet.get(i)) {
                set(i)
            }
        }
    }

    fun get(i: Int) = bitset.get(i)

    fun unset(i: Int) = bitset.set(i, false)

    fun unset(timeRangeBitSet: TimeRangeBitSet) {
        (0.. timeRangeBitSet.bitset.length()).forEach { i ->
            if (timeRangeBitSet.get(i)) {
                unset(i)
            }
        }
    }

    fun clear() = bitset.clear()

    fun contains(timeRangeBitSet: TimeRangeBitSet) = timeRangeBitSet.length() > 0 && or(timeRangeBitSet) == this

    fun length() = bitset.length()

    fun overlaps(timeRangeBitSet: TimeRangeBitSet): Boolean {
        (0.. timeRangeBitSet.bitset.length()).forEach { i ->
            if (bitset.get(i) && timeRangeBitSet.get(i)) {
                return true
            }
        }
        return false
    }

    fun and(timeRangeBitSet: TimeRangeBitSet): TimeRangeBitSet {
        val copy = BitSet()
        copy.or(bitset) // copy values from original
        copy.and(timeRangeBitSet.bitset)

        return TimeRangeBitSet(copy)
    }

    fun andInPlace(timeRangeBitSet: TimeRangeBitSet): TimeRangeBitSet {
        bitset.and(timeRangeBitSet.bitset)
        return this
    }

    fun or(timeRangeBitSet: TimeRangeBitSet): TimeRangeBitSet {
        val copy = bitset.clone() as BitSet
        copy.or(timeRangeBitSet.bitset)

        return TimeRangeBitSet(copy)
    }

    fun orInPlace(timeRangeBitSet: TimeRangeBitSet): TimeRangeBitSet {
        bitset.or(timeRangeBitSet.bitset)
        return this
    }

    fun shiftRightInPlace(): TimeRangeBitSet {
        val shifted = bitset.get(1, bitset.length())
        bitset.clear()
        bitset.or(shifted)
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as TimeRangeBitSet

        if (bitset != other.bitset) return false

        return true
    }

    override fun toString(): String {
        return "TimeRangeBitSet(bitset=$bitset)"
    }

    object Builder {
        fun from(range: Range): TimeRangeBitSet {
            val bitset = BitSet()
            val rangeUnits = (range.duration * 2).toInt() - 1
            val shiftStartTime = (range.startHour * 2).toInt()

            for (timeOffset in 0.. rangeUnits) {
                bitset[shiftStartTime + timeOffset] = true
            }
            return TimeRangeBitSet(bitset)
        }
    }

}
