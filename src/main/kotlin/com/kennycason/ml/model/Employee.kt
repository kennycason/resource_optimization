package com.kennycason.ml.model

import com.kennycason.ml.model.time.Range
import com.kennycason.ml.model.time.Shift
import com.kennycason.ml.model.time.Weekday
import org.eclipse.collections.api.list.ListIterable
import org.eclipse.collections.api.map.MapIterable
import org.eclipse.collections.api.map.MutableMap
import org.eclipse.collections.impl.factory.Lists

/**
 * Created by kenny on 11/7/16.
 */
class Employee(val person: Person,
               val shifts: MutableMap<Weekday, Shift>,
               val capableServices: ListIterable<Service>) {

    val totalHoursAvailable: Double =
            shifts.collectValues { weekday, shift -> shift.shift1.duration + shift.shift2.duration }
                  .fold(0.0) { l, r -> l + r }

    fun canAssign(appointmentRequest: AppointmentRequest): EmployeeAssignmentResult {
        if (appointmentRequest.time != null && appointmentRequest.duration != null) {
            throw IllegalStateException("Time and Duration can not both be set")
        }
        if (!canWorkDay(appointmentRequest.weekday)) { return EmployeeAssignmentResult(false) }

        // handle the case where time is requested
        if (appointmentRequest.time != null) {
            val shift = shifts.get(appointmentRequest.weekday)!!
            val assignmentResult = shift.canAssign(appointmentRequest.time)
            return EmployeeAssignmentResult(
                    assignmentResult.success,
                    appointmentRequest.time,
                    appointmentRequest.weekday)
        }

        // handle the case where no time is requested, assign to the first time slot available, if possible
        return findFirstEmptySlot(appointmentRequest)
    }

    fun assign(appointmentRequest: AppointmentRequest): EmployeeAssignmentResult {
        val assignmentResult = canAssign(appointmentRequest)
        if (!assignmentResult.success) {
            throw IllegalStateException("This appointment is not assignable!")
        }
        shifts[assignmentResult.weekday]!!.assign(assignmentResult.time!!)
        return assignmentResult
    }

//    fun assignIfPossible(appointmentRequest: AppointmentRequest): ShiftAssignmentResult {
////        if (!canAssign(appointmentRequest)) {
////            throw IllegalStateException("This appointment is not assignable!")
////        }
////        if (appointmentRequest.time != null && appointmentRequest.duration != null) {
////            throw IllegalStateException("Time and Duration can not both be set")
////        }
////        if (!canWorkDay(appointmentRequest.weekday)) { return AssignmentResult(false) }
//
//        // handle the case where time is requested
//        if (appointmentRequest.time != null) {
//            val shift = shifts.get(appointmentRequest.weekday)!!
//            val assignmentResult = shift.canAssign(appointmentRequest.time)
//            if (assignmentResult.success) {
//                shift.assign(appointmentRequest.time)
//            }
//          // return shift.assignIfPossible(appointmentRequest.time)
//        }
//        // handle the case where no time is requested, assign to the first time slot available, if possible
//        return assignToFirstEmptySlotIfPossible(appointmentRequest)
//    }

    // step through in 30 min chunks
    private fun findFirstEmptySlot(appointmentRequest: AppointmentRequest): EmployeeAssignmentResult {
        for (weekday in shifts.keys) {
            val assignmentResult = shifts[weekday]!!.canAssign(appointmentRequest.duration!!)
            if (assignmentResult.success) {
                return EmployeeAssignmentResult(
                        assignmentResult.success,
                        assignmentResult.time,
                        weekday)
            }
        }
        return EmployeeAssignmentResult(false)
    }

//    // step through in 30 min chunks
//    private fun assignToFirstEmptySlotIfPossible(appointmentRequest: AppointmentRequest): AssignmentResult {
//        for (shift in shifts) {
////            val assignmentResult = shift.assignIfPossible(appointmentRequest.duration!!)
//            val assignmentResult = shift.canAssign(appointmentRequest.duration!!)
//            if (assignmentResult.success) {
//                return shift.assign(appointmentRequest.duration)
//              //  return assignmentResult
//            }
//
//        }
//        return AssignmentResult(false)
//    }

    private fun canWorkDay(weekday: Weekday) = shifts.containsKey(weekday)
                                               && (shifts[weekday]!!.shift1.duration != 0.0
                                                   || shifts[weekday]!!.shift2.duration != 0.0)

    override fun toString(): String {
        return "Employee(capableServices=$capableServices, person=$person, shifts=$shifts, totalHoursAvailable=$totalHoursAvailable)"
    }


}
