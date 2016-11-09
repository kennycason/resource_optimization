package com.kennycason.ml.model.generation

import com.kennycason.ml.model.Employee
import org.eclipse.collections.api.list.ListIterable
import org.eclipse.collections.api.list.MutableList
import org.eclipse.collections.impl.factory.Lists
import java.util.*

/**
 * Created by kenny on 11/8/16.
 */
class EmployeeFactory {
    private val random = Random()
    private val peopleFactory = PeopleFactory()
    private val shiftFactory = ShiftFactory()
    private val serviceFactory = ServiceFactory()

    fun build(n: Int): ListIterable<Employee> {
        val employees: MutableList<Employee> = Lists.mutable.empty()

        (1.. n).forEach {
            employees.add(
                    Employee(person = peopleFactory.build(1).first,
                             shifts = shiftFactory.build(),
                             capableServices = serviceFactory.build(3))
                    )
        }

        return employees
    }

}