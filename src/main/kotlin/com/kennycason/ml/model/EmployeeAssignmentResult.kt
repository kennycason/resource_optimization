package com.kennycason.ml.model

import com.kennycason.ml.model.time.Range
import com.kennycason.ml.model.time.Weekday

/**
 * Created by kenny on 12/4/16.
 */
data class EmployeeAssignmentResult(val success: Boolean,
                                    val time: Range? = null,
                                    val weekday: Weekday? = null)