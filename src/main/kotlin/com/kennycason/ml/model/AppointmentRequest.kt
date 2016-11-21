package com.kennycason.ml.model

import com.kennycason.ml.model.time.Range
import com.kennycason.ml.model.time.Weekday

/**
 * Created by kenny on 11/7/16.
 *
 */
data class AppointmentRequest(val customer: Person,
                              val employee: Employee?,
                              val service: Service,
                              val weekday: Weekday,
                              // time contains both an exact time and duration
                              val time: Range? = null,
                              // duration is for when a time is not specified, only a duration,
                              // time & duration can not both be set
                              val duration: Double? = null)