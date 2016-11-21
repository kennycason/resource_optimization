package com.kennycason.ml.model

import com.kennycason.ml.algorithm.montecarlo.model.TimeRangeBitSet
import com.kennycason.ml.model.time.Range
import org.eclipse.collections.api.list.ListIterable
import org.eclipse.collections.api.map.MapIterable

/**
 * Created by kenny on 11/7/16.
 *
 * service time limits will be assumed to be allowed, as assignment requests must contain valid data
 */
class Room(val name: String,
           val services: ListIterable<Service>,
           private val serviceAssignments: MapIterable<String, TimeRangeBitSet> =
           services.toMap(Service::name, { s -> TimeRangeBitSet() })) {

    // assign time range
    fun assignIfPossible(service: Service, assignment: Range): Boolean {
        val serviceTimes = serviceAssignments.get(service.name)!!
        val assignmentTime = TimeRangeBitSet.Builder.from(assignment)

        if (serviceTimes.contains(assignmentTime)) {
            serviceTimes.orInPlace(assignmentTime)
        }
        return false
    }
}