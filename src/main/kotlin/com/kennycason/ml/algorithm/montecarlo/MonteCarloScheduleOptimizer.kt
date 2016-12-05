package com.kennycason.ml.algorithm.montecarlo

import com.kennycason.ml.algorithm.montecarlo.cost.ArrangementUtilizationFunction
import com.kennycason.ml.algorithm.montecarlo.cost.EmployeeUtilizationFunction
import com.kennycason.ml.algorithm.montecarlo.cost.RoomUtilizationFunction
import com.kennycason.ml.algorithm.montecarlo.model.Arrangement
import com.kennycason.ml.model.*
import com.kennycason.ml.model.time.Range
import com.kennycason.ml.model.time.RoomAssignmentResult
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
class MonteCarloScheduleOptimizer(private val office: Office,
                                  private val maxEmployeeAssignmentTries: Int = 1000,
                                  private val maxSwaps: Int = 10) {
    private val random = Random()
    private val arrangementUtilizationFunction = ArrangementUtilizationFunction(office)
    private val employeeUtilizationFunction = EmployeeUtilizationFunction(office)
    private val roomUtilizationFunction = RoomUtilizationFunction(office)

    fun balance(appointmentRequests: ListIterable<AppointmentRequest>) : ListIterable<Appointment> {
        println("Running simulation...")
        val appointments: MutableList<Appointment> = Lists.mutable.empty()
        val unassignedRequests: MutableList<AppointmentRequest> = Lists.mutable.empty()

        // initialize
        appointmentRequests.forEach { request ->
            var assigned = false
            for (i in 0.. maxEmployeeAssignmentTries) {
                // assign employee
                val employee = fetchRandomPersonByService(request.service)

                // no employee can perform service
                if (employee == null) { break }

                val employeeAssignmentResult = employee.canAssign(request)
                if (employeeAssignmentResult.success) {
                    employee.assign(request)

                    val roomAssignmentResult = assignRoomIfPossible(request.service, employeeAssignmentResult.time!!)
                    if (roomAssignmentResult.success) {
                        assigned = true
                        appointments.add(Appointment(
                                customer = request.customer,
                                employee = employee,
                                room = office.rooms.get(0)!!,
                                service = request.service,
                                weekday = request.weekday,
                                time = employeeAssignmentResult.time))
                        break

                    } else {
                        assigned = false
                    }
                }
            }
            if (!assigned) {
                unassignedRequests.add(request)
            }
        }

        swapAndAssign(appointments, appointmentRequests, unassignedRequests);

        println("total balance: ${arrangementUtilizationFunction.evaluate(appointments)}")
        println("employee balance: ${employeeUtilizationFunction.evaluate(appointments)}")
        println("room balance: ${roomUtilizationFunction.evaluate()}")
        println("[${appointments.size}] appoints assigned")
        println("[${unassignedRequests.size}] unassigned appoint requests")

       // println(office)
        return appointments
    }

    private fun swapAndAssign(appointments: MutableList<Appointment>,
                              appointmentRequests: ListIterable<AppointmentRequest>,
                              unassignedRequests: ListIterable<AppointmentRequest>) {

        (0.. maxSwaps).forEach { step ->
//            println("\nstep $step")
//            println("total balance: ${arrangementUtilizationFunction.evaluate(appointments)}")
//            println("employee balance: ${employeeUtilizationFunction.evaluate(appointments)}")
//            println("room balance: ${roomUtilizationFunction.evaluate()}")


        }

    }

    private fun assignRoomIfPossible(service: Service, time: Range): RoomAssignmentResult {
        // now assign room
        val possibleRooms = office.roomByServiceLookup[service.name]!!
        for (i in 0.. maxEmployeeAssignmentTries) {
            val randomRoom = possibleRooms[random.nextInt(possibleRooms.size)]
            val assignmentResult = randomRoom.canAssign(service, time)
            if (assignmentResult.success) {
                randomRoom.assign(service, time)
                return assignmentResult
            }
        }
        return RoomAssignmentResult(false)
    }

    private fun fetchRandomPersonByService(service: Service): Employee? {
        if (!office.employeeByServiceLookup.containsKey(service.name)) {
            return null
        }
        val employees = office.employeeByServiceLookup[service.name]!!
        return employees[random.nextInt(employees.size)]!!
    }

}