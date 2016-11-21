package com.kennycason.ml.model.time

import com.kennycason.ml.algorithm.montecarlo.model.TimeRangeBitSet
import com.kennycason.ml.model.AssignmentResult
import org.eclipse.collections.api.map.MapIterable
import org.eclipse.collections.impl.factory.Maps
import org.omg.PortableServer.IdAssignmentPolicyValue
import java.util.*

/**
 * Created by kenny on 11/7/16.
 *
 * note that the assigned data structure represents a bit set where each bit represents a 30 minute block of time
 * eventually all time ranges will be converted to bitsets for efficiency.
 */
class Shift(val shift1: Range = Range(0.0, 0.0),
            val shift2: Range = Range(0.0, 0.0),
            // a data structure to keep up with occupied times in integer form, all times are multiplied by two, meaning a 24 hour day has 48 units
            val assigned: TimeRangeBitSet = TimeRangeBitSet()) {

    // assign duration, with no specific time a shift, find first available
    fun assignIfPossible(duration: Double): AssignmentResult {
        val assignmentResult = assignIfPossible(duration, shift1)
        if (assignmentResult.success) { return assignmentResult }
        return assignIfPossible(duration, shift2)
    }

    private fun assignIfPossible(duration: Double, shift: Range): AssignmentResult {
        // multiply by two so we can iterate over more cleanly
        val rangeUnits = (duration * 2).toInt() - 1
        val shiftStartTime = (shift.startHour * 2).toInt()
        val maxTime = shiftStartTime + ((shift.duration - duration) * 2).toInt()
        for (possibleAssignmentStartTime in shiftStartTime.. maxTime) { // start walking up the time slots
            var canAssign = true
            for (timeOffset in 0.. rangeUnits) {
                if (assigned.get(possibleAssignmentStartTime + timeOffset)) {
                    canAssign = false
                    break
                }
            }
            if (canAssign) {
                for (timeOffset in 0.. rangeUnits) {
                    assigned.set(possibleAssignmentStartTime + timeOffset)
                }
                val rangeStart = possibleAssignmentStartTime.toDouble() / 2.0 // convert back to double
                return AssignmentResult(true, Range(rangeStart, rangeStart + duration))
            }
        }
        return AssignmentResult(false)
    }

    // an exact time range to an exact shift
    fun assignIfPossible(assignment: Range): AssignmentResult {
        val assignmentResult = assignIfPossible(assignment, shift1)
        if (assignmentResult.success) { return assignmentResult }
        return assignIfPossible(assignment, shift2)
    }

    private fun assignIfPossible(assignment: Range, shift: Range): AssignmentResult {
        if (!shift.contains(assignment)) { return AssignmentResult(false) }

        // multiply by two so we can iterate over more cleanly
        val rangeUnits = (assignment.duration * 2).toInt() - 1
        val assignmentStartTime = (assignment.startHour * 2).toInt()
        for (timeOffset in assignmentStartTime.. assignmentStartTime + rangeUnits) {
            if (assigned.get(timeOffset)) {
                return AssignmentResult(false)
            }
        }
        // if we got here, we can assign the time
        for (timeOffset in assignmentStartTime.. assignmentStartTime + rangeUnits) {
            assigned.set(timeOffset)
        }
        return AssignmentResult(true, assignment)
    }

    fun unassign(assignment: Range) {
        assigned.unset(TimeRangeBitSet.Builder.from(assignment))
    }

    fun clear() {
        assigned.clear()
    }

    override fun toString(): String {
        return "Shift(assigned=$assigned, shift1=$shift1, shift2=$shift2)"
    }

}