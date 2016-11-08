package com.kennycason.ml.model

import com.kennycason.ml.model.time.Range
import java.time.Duration

/**
 * Created by kenny on 11/7/16.
 *
 * This data class serves as both an appointment request, and the assigned appointment
 */
data class Appointment(val customer: Customer,
                       val employee: Employee,
                       val service: Service,
                       val time: Range,
                       val room: Room)