package com.kennycason.ml.algorithm.montecarlo.cost

import com.kennycason.ml.algorithm.montecarlo.model.Arrangement
import com.kennycason.ml.model.Office
import org.eclipse.collections.impl.factory.Maps

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

    fun evaluate(): Double {
        // create a zeroed entry for each room
        var total = 0.0
        var balance = 0.0
        office.rooms.forEach { room ->
            balance += room.totalAssignedTime()
            total += office.businessHours.duration * room.services.size()
            // println("${room.totalAssignedTime()} / ${office.businessHours.duration * room.services.size()}")
        }

        return balance / total
    }
}