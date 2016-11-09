package com.kennycason.ml.model.generation

import com.kennycason.ml.model.time.Weekday
import java.util.*

/**
 * Created by kenny on 11/8/16.
 */
class WeekdayFactory {
    private val random = Random()

    fun build() = when (random.nextInt(7)) {
        0 -> Weekday.MONDAY
        1 -> Weekday.TUESDAY
        2 -> Weekday.WEDNESDAY
        3 -> Weekday.THURSDAY
        4 -> Weekday.FRIDAY
        5 -> Weekday.SATURDAY
        6 -> Weekday.SUNDAY
        else -> throw IllegalStateException("Invalid week day > 6")
    }

}