package com.kennycason.ml.model

import com.kennycason.ml.model.time.Range
import org.eclipse.collections.api.list.ListIterable
import org.eclipse.collections.impl.factory.Maps
import java.time.Duration

/**
 * Created by kenny on 11/7/16.
 *
 * this data structure represents the total resources of an office in terms of employees and rooms
 */
data class Office(val employees: ListIterable<Employee>,
                  val rooms: ListIterable<Room>,
                  val businessHours: Range = Range(8.0, 5.0),
                  // convenience data structures for quick lookup during cost function evaluation
                  val employeeNameLookup: MutableMap<String, Employee> = employees.toMap(Employee::name, {e -> e}),
                  val roomNameLookup: MutableMap<String, Room> = rooms.toMap(Room::name, {r -> r}))
