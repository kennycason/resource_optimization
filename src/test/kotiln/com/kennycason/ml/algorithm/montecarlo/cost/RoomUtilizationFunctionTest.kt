package com.kennycason.ml.algorithm.montecarlo.cost

import com.kennycason.ml.algorithm.montecarlo.model.Arrangement
import com.kennycason.ml.model.*
import com.kennycason.ml.model.time.Range
import com.kennycason.ml.model.time.Shift
import com.kennycason.ml.model.time.Weekday
import org.eclipse.collections.impl.factory.Lists
import org.eclipse.collections.impl.factory.Maps
import org.junit.Test
import org.mockito.Mockito
import kotlin.test.assertEquals

/**
 * Created by kenny on 11/8/16.
 */
class RoomUtilizationFunctionTest {

    @Test
    fun basic() {
        // test data
        val employee = Employee(person = Person(name = "mock employee", gender = Gender.MALE), shifts = Maps.immutable.empty(), capableServices = Lists.immutable.empty())
        val person = Person(name = "mock customer", gender = Gender.MALE)
        val service = Service(name = "mock service", possibleDurations = Lists.immutable.empty())

        val room1 = Room(name = "room 1", services = Lists.immutable.of(service))
        val room2 = Room(name = "room 2", services = Lists.immutable.of(service))
        val room3 = Room(name = "room 3", services = Lists.immutable.of(service))

        val appointment1 = Appointment(
                customer = person,
                employee = employee,
                weekday = Weekday.MONDAY,
                time = Range(8.0, 12.0),
                service = service,
                room = room1)
        val appointment2 = Appointment(
                customer = person,
                employee = employee,
                weekday = Weekday.MONDAY,
                time = Range(8.0, 12.0),
                service = service,
                room = room2)
        val appointment3 = Appointment(
                customer = person,
                employee = employee,
                weekday = Weekday.MONDAY,
                time = Range(8.0, 12.0),
                service = service,
                room = room3)

        // test arrangement utilization
        val office = Office(
                employees = Lists.immutable.empty(),
                businessHours = Range(8.0, 16.0),
                rooms = Lists.immutable.of(room1, room2, room3))

        room1.assignIfPossible(service, appointment1.time!!)
        room2.assignIfPossible(service, appointment2.time!!)
        room3.assignIfPossible(service, appointment3.time!!)

        val roomUtitlizationFunction = RoomUtilizationFunction(office = office)

        // should be 0.5 as each room is providing one service that takes half of their time
        assertEquals(0.5, roomUtitlizationFunction.evaluate())

        // should be 2/6 (0.33) as only two rooms are providing service, spending half of their time,
        // and one room is not being used
        room3.unassign(service, appointment3.time)
        assertEquals(0.3333333333333333, roomUtitlizationFunction.evaluate())
    }
}