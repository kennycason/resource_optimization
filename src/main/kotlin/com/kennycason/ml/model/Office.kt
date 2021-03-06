package com.kennycason.ml.model

import com.kennycason.ml.model.time.Range
import org.eclipse.collections.api.list.ListIterable
import org.eclipse.collections.api.list.MutableList
import org.eclipse.collections.api.map.MapIterable
import org.eclipse.collections.impl.factory.Lists
import org.eclipse.collections.impl.factory.Maps

/**
 * Created by kenny on 11/7/16.
 *
 * this data structure represents the total resources of an office in terms of employees and rooms
 */
class Office(val employees: ListIterable<Employee>,
             val rooms: ListIterable<Room>,
             val businessHours: Range = Range(8.0, 17.0),
             // convenience data structures for quick lookup during cost function evaluation
             val employeeNameLookup: MutableMap<String, Employee> = employees.toMap({e -> e.person.name}, {e -> e}),
             val roomNameLookup: MutableMap<String, Room> = rooms.toMap(Room::name, {r -> r}),
             val employeeByServiceLookup: MutableMap<String, MutableList<Employee>> = Maps.mutable.empty(),
             val roomByServiceLookup: MutableMap<String, MutableList<Room>> = Maps.mutable.empty()) {

    init  {
        employees.forEach { employee ->
            employee.capableServices.forEach { capability ->
                if (!employeeByServiceLookup.containsKey(capability.name)) {
                    employeeByServiceLookup.put(capability.name, Lists.mutable.empty())
                }
                employeeByServiceLookup.get(capability.name)!!.add(employee)
            }
        }

        rooms.forEach { room ->
            room.services.forEach { service ->
                if (!roomByServiceLookup.containsKey(service.name)) {
                    roomByServiceLookup.put(service.name, Lists.mutable.empty())
                }
                roomByServiceLookup.get(service.name)!!.add(room)
            }
        }
    }

    override fun toString(): String {
        return "Office(businessHours=$businessHours, employees=$employees, rooms=$rooms, employeeNameLookup=$employeeNameLookup, roomNameLookup=$roomNameLookup, employeeByServiceLookup=$employeeByServiceLookup)"
    }

}

