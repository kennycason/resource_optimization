package com.kennycason.ml.model

import com.kennycason.ml.algorithm.montecarlo.model.TimeRangeBitSet
import com.kennycason.ml.model.time.Range
import com.kennycason.ml.model.time.RoomAssignmentResult
import com.kennycason.ml.model.time.ShiftAssignmentResult
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
    fun canAssign(service: Service, assignment: Range): RoomAssignmentResult {
        val serviceTimes = serviceAssignments.get(service.name)!!
        val assignmentTime = TimeRangeBitSet.Builder.from(assignment)

        if (!serviceTimes.overlaps(assignmentTime)) {
            return RoomAssignmentResult(true, assignment)
        }
        return RoomAssignmentResult(false)
    }

    fun assign(service: Service, assignment: Range): RoomAssignmentResult {
        val assignmentResult = canAssign(service, assignment)
        if (!assignmentResult.success) {
            throw IllegalStateException("This appointment is not assignable!")
        }
        val serviceTimes = serviceAssignments[service.name]!!
        val assignmentTime = TimeRangeBitSet.Builder.from(assignment)

        serviceTimes.orInPlace(assignmentTime)
        return assignmentResult
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