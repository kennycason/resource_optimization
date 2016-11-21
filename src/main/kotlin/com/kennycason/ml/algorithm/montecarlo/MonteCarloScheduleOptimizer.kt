package com.kennycason.ml.algorithm.montecarlo

import com.kennycason.ml.algorithm.montecarlo.cost.ArrangementUtilizationFunction
import com.kennycason.ml.algorithm.montecarlo.cost.ArrangementsUtilizationFunction
import com.kennycason.ml.algorithm.montecarlo.cost.EmployeeUtilizationFunction
import com.kennycason.ml.algorithm.montecarlo.cost.RoomUtilizationFunction
import com.kennycason.ml.algorithm.montecarlo.model.Arrangement
import com.kennycason.ml.model.*
import com.kennycason.ml.model.time.Range
import org.eclipse.collections.api.RichIterable
import org.eclipse.collections.api.list.ListIterable
import org.eclipse.collections.api.list.MutableList
import org.eclipse.collections.impl.factory.Lists
import java.util.*

/**
 * Created by kenny on 11/13/16.
 *
 * 1. pick a random suitable person,
 * 2. pick first available room
 * 3. repeat until all requests are went through
 * 4. begin running simulations of various appointment swaps in an attempt to find max balanced schedule
 */
class MonteCarloScheduleOptimizer(private val office: Office) {
    private val random = Random()
    private val maxEmployeeAssignmentTries = 10
    private val maxRoomAssignmentTries = 10
    private val arrangementUtilizationFunction = ArrangementUtilizationFunction(office)
    private val employeeUtilizationFunction = EmployeeUtilizationFunction(office)
    private val roomUtilizationFunction = RoomUtilizationFunction(office)

    fun balance(appointmentRequests: ListIterable<AppointmentRequest>) : Arrangement {
        println("Running simulation...")
        val appointments: MutableList<Appointment> = Lists.mutable.empty()
        val unassignedRequests: MutableList<AppointmentRequest> = Lists.mutable.empty()

        // initialize
        appointmentRequests.forEach { request ->
            var assigned = false
            for (i in 0.. maxEmployeeAssignmentTries) {
                // assign employee
                val employee = fetchRandomPersonByService(request.service)
                val employeeAssignmentResult = employee.assignIfPossible(request)
                if (employeeAssignmentResult.success) {
                    val roomAssignmentSuccess = assignRoom(request.service, employeeAssignmentResult.time!!)
                    if (roomAssignmentSuccess) {
                        assigned = true
                        appointments.add(Appointment(
                                customer = request.customer,
                                employee = employee,
                                room = office.rooms.get(0)!!,
                                service = request.service,
                                weekday = request.weekday,
                                time = employeeAssignmentResult.time!!))
                        break

                    } else { // undo employee assignment
                        employee.shifts[request.weekday].unassign(employeeAssignmentResult.time)
                        assigned = false
                    }
                }
            }
            if (!assigned) {
                unassignedRequests.add(request)
            }
        }
        // swap and add
        // TODO

        val arrangement = Arrangement(appointments)

        println("total balance: ${arrangementUtilizationFunction.evaluate(arrangement)}")
        println("employee balance: ${employeeUtilizationFunction.evaluate(arrangement)}")
        println("room balance: ${roomUtilizationFunction.evaluate()}")
        println("[${appointments.size}] appoints assigned")
        println("[${unassignedRequests.size}] unassigned appoint requests")

       // println(office)
        return arrangement
    }

    private fun assignRoom(service: Service, time: Range): Boolean {
        // now assign room
        val possibleRooms = office.roomByServiceLookup[service.name]!!
        for (i in 0.. maxEmployeeAssignmentTries) {
            val randomRoom = possibleRooms[random.nextInt(possibleRooms.size)]
            val assignmentResult = randomRoom.assignIfPossible(service, time)
            if (assignmentResult.success) {
                return true
            }
        }
        return false
    }

    private fun fetchRandomPersonByService(service: Service): Employee {
        val employees = office.employeeByServiceLookup.get(service.name)!!
        return employees[random.nextInt(employees.size)]!!
    }

}