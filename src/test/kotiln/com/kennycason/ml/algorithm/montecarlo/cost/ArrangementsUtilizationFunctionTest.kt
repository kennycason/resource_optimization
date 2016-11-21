package com.kennycason.ml.algorithm.montecarlo.cost

import com.kennycason.ml.algorithm.montecarlo.model.Arrangement
import com.kennycason.ml.model.*
import com.kennycason.ml.model.generation.EmployeeFactory
import com.kennycason.ml.model.generation.RoomFactory
import com.kennycason.ml.model.time.Range
import com.kennycason.ml.model.time.Shift
import com.kennycason.ml.model.time.Weekday
import org.eclipse.collections.impl.factory.Lists
import org.junit.Test
import org.mockito.Mockito
import kotlin.test.assertEquals

/**
 * Created by kenny on 11/13/16.
 */
class ArrangementsUtilizationFunctionTest {

    @Test
    fun basic() {
        val employeeFactory = EmployeeFactory()
        val roomFactory = RoomFactory()
        val office = Office(
                employees = employeeFactory.build(10),
                rooms = roomFactory.build(5))

        val arrangementUtilizationFunction = ArrangementUtilizationFunction(
                office = office,
                roomBalanceWeight = 0.5,
                employeeWeight = 0.5)

        val arrangementsUtilizationFunction = ArrangementsUtilizationFunction(arrangementUtilizationFunction)
       // arrangementsUtilizationFunction.evaluate()

    }
}