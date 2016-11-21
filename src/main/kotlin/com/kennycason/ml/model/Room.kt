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
    fun assignIfPossible(service: Service, assignment: Range): AssignmentResult {
        val serviceTimes = serviceAssignments.get(service.name)!!
        val assignmentTime = TimeRangeBitSet.Builder.from(assignment)

        if (!serviceTimes.overlaps(assignmentTime)) {
            serviceTimes.orInPlace(assignmentTime)
            return AssignmentResult(true, assignment)
        }
        return AssignmentResult(false)
    }

    fun totalAssignedTime(): Double {
        var total = 0.0
        serviceAssignments.forEachKey { serviceName ->
            total += totalAssignedTime(serviceName = serviceName)
        }

        return total
    }

    fun totalAssignedTime(serviceName: String): Double {
        var total = 0.0
        val timeRangeBitSit = serviceAssignments.get(serviceName)
        (0.. timeRangeBitSit.length()).forEach { i ->
            if (timeRangeBitSit.get(i)) {
                total += 0.5
            }
        }

        return total
    }

    fun unassign(service: Service, time: Range) {
        serviceAssignments.get(service.name)!!.unset(TimeRangeBitSet.Builder.from(time))
    }

    override fun toString(): String {
        return "Room(name='$name', services=$services, serviceAssignments=$serviceAssignments)"
    }


}