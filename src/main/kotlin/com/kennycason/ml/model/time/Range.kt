package com.kennycason.ml.model.time

import java.math.BigInteger
import java.util.*

/**
 * Created by kenny on 11/8/16.
 *
 * values are hours in military time. 0.5 chunks equaling 30 min
 * TODO replace this shit with bit sets for faster time comparison/lookup, need to create immutable bitset
 */
data class Range(val startHour: Double,
                 val endHour: Double,
                 val duration: Double = endHour - startHour) {

    fun contains(range: Range) = range.startHour >= startHour && range.endHour <= endHour

    fun overlaps(range: Range) = (range.startHour > startHour && range.startHour < endHour)
                                 || (range.endHour > startHour && range.endHour < endHour)


}