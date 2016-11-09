package com.kennycason.ml.algorithm.montecarlo.cost

import com.kennycason.ml.algorithm.montecarlo.model.Arrangement
import com.kennycason.ml.model.*
import com.kennycason.ml.model.time.Range
import com.kennycason.ml.model.time.Shift
import com.kennycason.ml.model.time.Weekday
import org.eclipse.collections.impl.factory.Lists
import org.junit.Test
import org.mockito.Mockito
import kotlin.test.assertEquals

/**
 * Created by kenny on 11/8/16.
 */
class EmployeeUtilizationFunctionTest {

    @Test
    fun basic() {
        // test data
        val customer = Person(name = "mock customer", gender = Gender.MALE)
        val room = Room(name = "mock room", services = Lists.immutable.empty())
        val service = Service(name = "mock service", possibleDurations = Lists.immutable.empty())

        // three 8 hour employees
        val employee1 = Employee(
                person = Person(name = "kenny", gender = Gender.MALE),
                shifts = Lists.immutable.of(Shift(Weekday.MONDAY, shift1 = Range(8.0, 12.0), shift2 = Range(13.0, 17.0))),
                capableServices = Lists.immutable.empty())
        val employee2 = Employee(
                person = Person(name = "addam", gender = Gender.MALE),
                shifts = Lists.immutable.of(Shift(Weekday.MONDAY, shift1 = Range(8.0, 12.0), shift2 = Range(13.0, 17.0))),
                capableServices = Lists.immutable.empty())
        val employee3 = Employee(
                person = Person(name = "andrew", gender = Gender.MALE),
                shifts = Lists.immutable.of(Shift(Weekday.MONDAY, shift1 = Range(8.0, 12.0), shift2 = Range(13.0, 17.0))),
                capableServices = Lists.immutable.empty())

        // build arrangements (i.e. sample appointments)
        val appointment1 = Appointment(
                customer = customer,
                employee = employee1,
                time = Range(8.0, 12.0),
                room = room,
                service = service)
        val appointment2 = Appointment(
                customer = customer,
                employee = employee1,
                time = Range(8.0, 12.0),
                room = room,
                service = service)
        val appointment3 = Appointment(
                customer = customer,
                employee = employee1,
                time = Range(8.0, 12.0),
                room = room,
                service = service)

        // test arrangement utilization
        val office = Office(
                employees = Lists.immutable.of(employee1, employee2, employee3),
                rooms = Lists.immutable.empty())
        val employeeUtilizationFunction = EmployeeUtilizationFunction(office = office)

        // should be 0.5 as each employee is providing one service that takes half of their time
        val arrangement = Arrangement(appointments = Lists.immutable.of(appointment1, appointment2, appointment3))
        assertEquals(0.5, employeeUtilizationFunction.evaluate(arrangement))

        // should be 2/6 (0.33) as only two employees are providing service, spending half of their time,
        // and one employee is being lazy and not doing anything
        val arrangement2 = Arrangement(appointments = Lists.immutable.of(appointment1, appointment2))
        assertEquals(0.3333333333333333, employeeUtilizationFunction.evaluate(arrangement2))
    }
}