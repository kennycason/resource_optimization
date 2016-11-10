package com.kennycason.ml.model.generation

import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by kenny on 11/8/16.
 */
class EmployeeFactoryTest {

    @Test
    fun employeeGeneration() {
        val employeeFactory = EmployeeFactory()

        for (employee in employeeFactory.build(5)) {
            println(employee)
        }
    }
}