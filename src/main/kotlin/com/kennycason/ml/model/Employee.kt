package com.kennycason.ml.model

import com.kennycason.ml.model.time.Shift
import org.eclipse.collections.api.list.ListIterable

/**
 * Created by kenny on 11/7/16.
 */
data class Employee(val person: Person,
                    val shifts: ListIterable<Shift>,
                    val capableServices: ListIterable<Service>,
                    val totalHoursAvailable: Double =
                            shifts.map { shift -> shift.shift1.duration + shift.shift2.duration }
                                  .fold(0.0) { l, r -> l + r }) // fold instead of map in case collection is empty
