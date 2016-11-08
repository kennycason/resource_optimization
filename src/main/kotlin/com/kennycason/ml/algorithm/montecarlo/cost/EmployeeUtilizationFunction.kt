package com.kennycason.ml.algorithm.montecarlo.cost

import com.kennycason.ml.algorithm.montecarlo.model.Arrangement
import com.kennycason.ml.model.Office
import org.eclipse.collections.impl.factory.Maps
import java.time.Duration

/**
 * Created by kenny on 11/7/16.
 * this utilization function will calculate the balance of the average of employee time used.
 * 1.0 means every one's time is 100% utilized, 0.0 means zero utilization.
 *
 * 1/n ∑ percent_of_employee_time_utilized, where n = the number of people
 *
 * Note: Assumes all arrangements are valid
 */
class EmployeeUtilizationFunction(val office: Office) {

    fun evaluate(arrangement: Arrangement): Double {
        val employeeHours: MutableMap<String, Double> = Maps.mutable.empty()
        // create a zeroed entry for each employee
        office.employees.forEach { employee -> employeeHours.put(employee.name, 0.0) }

        // tally up hours worked for each employee
        arrangement.appointments.forEach { appointment ->
            employeeHours.put(appointment.employee.name,
                    employeeHours.get(appointment.employee.name)!! + appointment.time.duration)
        }

        // calculate balance
        var balance = 0.0
        for ((name, employee) in office.employeeNameLookup) {
            balance += employeeHours.get(name)!! / employee.totalHoursAvailable
        }

        return balance / employeeHours.size
    }
}