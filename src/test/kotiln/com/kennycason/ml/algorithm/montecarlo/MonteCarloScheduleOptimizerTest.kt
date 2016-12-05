package com.kennycason.ml.algorithm.montecarlo

import com.kennycason.ml.model.*
import com.kennycason.ml.model.generation.AppointmentRequestFactory
import com.kennycason.ml.model.generation.EmployeeFactory
import com.kennycason.ml.model.generation.RoomFactory
import com.kennycason.ml.model.generation.ServiceFactory
import org.eclipse.collections.api.list.ListIterable
import org.eclipse.collections.impl.factory.Lists
import org.eclipse.collections.impl.factory.Maps
import org.junit.Test

/**
 * Created by kenny on 11/20/16.
 */
class MonteCarloScheduleOptimizerTest {
    private val employeeFactory = EmployeeFactory()
    private val roomFactory = RoomFactory()

    @Test
    fun schedule() {
        val employees = employeeFactory.build(30)
        val rooms = roomFactory.build(26)
        val services: ListIterable<Service> = rooms.flatCollect(Room::services)

        val appointmentRequestFactory = AppointmentRequestFactory(availableEmployees = employees, availableServices = services)
        val office = Office(employees = employees, rooms = rooms)

        val monteCarloScheduleOptimizer = MonteCarloScheduleOptimizer(office)
        val appointmentRequests = appointmentRequestFactory.build(250)

        val appointments = monteCarloScheduleOptimizer.balance(appointmentRequests)
    }

}