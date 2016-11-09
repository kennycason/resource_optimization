package com.kennycason.ml.model

import com.kennycason.ml.model.time.Range
import com.kennycason.ml.model.time.Shift
import com.kennycason.ml.model.time.Weekday
import org.eclipse.collections.impl.factory.Lists
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by kenny on 11/8/16.
 */
class EmployeeTest {

    @Test
    fun totalHours() {
        val employee = Employee(
                person = Person(name = "kenny", gender = Gender.MALE),
                shifts = Lists.immutable.empty(),
                capableServices = Lists.immutable.empty())
        assertEquals(0.0, employee.totalHoursAvailable)

        val employee2 = Employee(
                person = Person(name = "kenny", gender = Gender.MALE),
                shifts = Lists.immutable.of(Shift(Weekday.MONDAY, shift1 = Range(8.0, 12.0), shift2 = Range(13.0, 17.0))),
                capableServices = Lists.immutable.empty())
        assertEquals(8.0, employee2.totalHoursAvailable)

        val employee3 = Employee(
                person = Person(name = "kenny", gender = Gender.MALE),
                shifts = Lists.immutable.of(
                        Shift(Weekday.MONDAY, shift1 = Range(8.0, 12.0), shift2 = Range(13.0, 17.0)),
                        Shift(Weekday.TUESDAY, shift2 = Range(13.5, 17.5)),
                        Shift(Weekday.WEDNESDAY, shift1 = Range(8.5, 12.5))),
                capableServices = Lists.immutable.empty())
        assertEquals(16.0, employee3.totalHoursAvailable)
    }

}