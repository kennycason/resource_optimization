package com.kennycason.ml.model

import com.kennycason.ml.model.time.Range
import com.kennycason.ml.model.time.Shift
import com.kennycason.ml.model.time.Weekday
import org.eclipse.collections.api.list.ListIterable
import org.eclipse.collections.api.map.MapIterable
import org.eclipse.collections.impl.factory.Lists

/**
 * Created by kenny on 11/7/16.
 */
class Employee(val person: Person,
               val shifts: MapIterable<Weekday, Shift>,
               val capableServices: ListIterable<Service>) {

    val totalHoursAvailable: Double =
            shifts.map { shift -> shift.shift1.duration + shift.shift2.duration }
                  .fold(0.0) { l, r -> l + r }

    fun assignIfPossible(appointmentRequest: AppointmentRequest): AssignmentResult {
        if (!canWorkDay(appointmentRequest.weekday)) { return AssignmentResult(false) }
        if (appointmentRequest.time != null && appointmentRequest.duration != null) {
            throw IllegalStateException("Time and Duration can not both be set")
        }

        // handle the case where time is requested
        if (appointmentRequest.time != null) {
            val shift = shifts.get(appointmentRequest.weekday)!!
            return shift.assignIfPossible(appointmentRequest.time)
        }
        // handle the case where no time is requested, assign to the first time slot available, if possible
        return assignToFirstEmptySlotIfPossible(appointmentRequest)
    }

    // step through in 30 min chunks
    private fun assignToFirstEmptySlotIfPossible(appointmentRequest: AppointmentRequest): AssignmentResult {
        for (shift in shifts) {
            val assignmentResult = shift.assignIfPossible(appointmentRequest.duration!!)
            if (assignmentResult.success) {
                return assignmentResult
            }

        }
        return AssignmentResult(false)
    }

    private fun canWorkDay(weekday: Weekday) = shifts.containsKey(weekday)
                                               && (shifts.get(weekday).shift1.duration != 0.0
                                                   || shifts.get(weekday).shift2.duration != 0.0)

    override fun toString(): String {
        return "Employee(capableServices=$capableServices, person=$person, shifts=$shifts, totalHoursAvailable=$totalHoursAvailable)"
    }


}
