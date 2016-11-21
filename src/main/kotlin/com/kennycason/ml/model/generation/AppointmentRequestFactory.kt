package com.kennycason.ml.model.generation

import com.kennycason.ml.model.AppointmentRequest
import com.kennycason.ml.model.Employee
import com.kennycason.ml.model.Service
import com.kennycason.ml.model.time.Range
import com.kennycason.ml.model.time.Weekday
import org.eclipse.collections.api.list.ListIterable
import org.eclipse.collections.api.list.MutableList
import org.eclipse.collections.impl.factory.Lists
import java.util.*

/**
 * Created by kenny on 11/8/16.
 */
class AppointmentRequestFactory(private val availableServices: ListIterable<Service>,
                                private val availableEmployees: ListIterable<Employee>) {
    private val random = Random()
    private val peopleFactory = PeopleFactory()
    private val shiftFactory = ShiftFactory()
    private val weekdayFactory = WeekdayFactory()

    fun build(n: Int): ListIterable<AppointmentRequest> {
        val appointmentRequests: MutableList<AppointmentRequest> = Lists.mutable.empty()

        (1.. n).forEach {
            val service = availableServices[random.nextInt(availableServices.size())]
            val duration = service.possibleDurations[random.nextInt(service.possibleDurations.size())]
            val time = buildTime(service)

            appointmentRequests.add(
                    AppointmentRequest(customer = peopleFactory.build(1).first,
                            service = service,
                            employee = buildEmployee(),
                            weekday = weekdayFactory.build(),
                            time = time,
                            duration = if (time == null) { duration } else { null }))
        }

        return appointmentRequests
    }

    private fun buildEmployee(): Employee? {
        if (random.nextInt(4) > 0) { return null } // give a 3/4 prob of selecting employee

        return availableEmployees[random.nextInt(availableEmployees.size())]
    }

    private fun buildTime(service: Service): Range? {
        if (random.nextInt(4) == 0) { return null } // give a 1/4 prob of not selecting time

        val duration = service.possibleDurations[random.nextInt(service.possibleDurations.size())]
        while (true) {
            val halfHours = random.nextInt(18) // 30 min intervals, exclude times that overlap with lunch
            val startHour = 8.0 + halfHours * 0.5 // 8 is the start time, 8am.
            val endHour = startHour + duration
            if ((startHour < 12.0 && endHour <= 12.0)
                    || (startHour >= 13.0 && endHour <= 17.0)) {
                return Range(startHour = startHour, endHour = endHour)
            }
        }
    }

}