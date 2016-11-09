package com.kennycason.ml.model.generation

import org.eclipse.collections.api.list.ListIterable
import org.eclipse.collections.impl.factory.Sets
import org.eclipse.collections.impl.list.mutable.ListAdapter
import java.util.*

/**
 * Created by kenny on 11/8/16.
 */
class ServiceDurationFactory {
    private val random = Random()

    fun build(n: Int): ListIterable<Double> {
        if (n > 4) { throw IllegalStateException("$n service durations must be <= 4") }

        val serviceDurations: MutableSet<Double> = Sets.mutable.empty()

        while (serviceDurations.size < n) {
           val durationInHours = when (random.nextInt(4)) {
                0 -> 0.5
                1 -> 1.0
                2 -> 1.5
                3 -> 2.0
                else -> throw IllegalStateException("Possible duration in hours")
            }
            serviceDurations.add(durationInHours)
        }
        return ListAdapter.adapt(
                serviceDurations
                        .toList()
                        .sorted())
    }

}