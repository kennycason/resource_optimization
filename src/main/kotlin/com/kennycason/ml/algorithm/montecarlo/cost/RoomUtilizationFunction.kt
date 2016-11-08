package com.kennycason.ml.algorithm.montecarlo.cost

import com.kennycason.ml.algorithm.montecarlo.model.Arrangement
import com.kennycason.ml.model.Office
import org.eclipse.collections.impl.factory.Maps
import java.time.Duration

/**
 * Created by kenny on 11/7/16.
 * this utilization function will calculate the balance of the average of room resources used.
 * 1.0 means every room is 100% utilized, 0.0 means zero utilization.
 *
 * 1/n ∑ percent_of_room_utilized, where n = the number of rooms
 *
 * Note: Assumes all arrangements are valid
 */
class RoomUtilizationFunction(val office: Office) {

    fun evaluate(arrangement: Arrangement): Double {
        val roomHours: MutableMap<String, Double> = Maps.mutable.empty()
        // create a zeroed entry for each room
        office.rooms.forEach { room -> roomHours.put(room.name, 0.0) }

        // tally up hours worked for each employee
        arrangement.appointments.forEach { appointment ->
            roomHours.put(appointment.room.name,
                    roomHours.get(appointment.room.name)!! + appointment.time.duration)
        }

        // calculate balance
        var balance = 0.0
        for ((name, room) in office.roomNameLookup) {
            balance += roomHours.get(name)!! / office.businessHours.duration
        }

        return balance / roomHours.size
    }
}