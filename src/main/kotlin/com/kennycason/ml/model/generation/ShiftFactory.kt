package com.kennycason.ml.model.generation

import com.kennycason.ml.model.time.Range
import com.kennycason.ml.model.time.Shift
import com.kennycason.ml.model.time.Weekday
import org.eclipse.collections.api.list.ListIterable
import org.eclipse.collections.api.list.MutableList
import org.eclipse.collections.api.map.MapIterable
import org.eclipse.collections.api.map.MutableMap
import org.eclipse.collections.impl.factory.Lists
import org.eclipse.collections.impl.factory.Maps
import java.util.*

/**
 * Created by kenny on 11/8/16.
 */
class ShiftFactory {
    private val random = Random()

    fun build(): MapIterable<Weekday, Shift> {
        val shifts: MutableMap<Weekday, Shift> = Maps.mutable.empty()
        for (weekday in Weekday.values()) {
            shifts.put(weekday, Shift(
                    shift1 = if (random.nextBoolean()) { Range(8.0, 12.0) } else { Range(0.0, 0.0) },
                    shift2 = if (random.nextBoolean()) { Range(13.0, 17.0) } else { Range(0.0, 0.0) }
            ))
        }
        if (shifts.isEmpty()) { return build() }

        return shifts
    }

}