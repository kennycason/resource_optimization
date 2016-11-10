package com.kennycason.ml.model.generation

import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by kenny on 11/8/16.
 */
class AppointmentRequestFactoryTest {

    @Test
    fun appointmentRequestGeneration() {
        val employeeFactory = EmployeeFactory()
        val serviceFactory = ServiceFactory()
        val appointmentRequestFactory = AppointmentRequestFactory(
                availableEmployees = employeeFactory.build(10),
                availableServices = serviceFactory.build(5))

        for (appointmentRequest in appointmentRequestFactory.build(50)) {
            println(appointmentRequest)
        }
    }
}