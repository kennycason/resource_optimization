# Resource Optimization via Monte-Carlo

## 1. Given:

### Employees
  * 1-n Employees.
  * Employees have 1-n capabilities.
  * Employees have a set shift for the week.
  * Employees have 1 hour lunch breaks pre-scheduled each day.
  * Employees weekly shifts are scheduled manually and in advance of the week starting.
  * Employees don't have to work an entire day.
  * Employees can be male or female.

### Employee Shifts
  * Work days are 7 days per week.
  * Work hours are 9am to 7pm.

### Rooms
  * 1-n Rooms
  * Rooms have an capacity. i.e. they can only accommodate services that fit their capacity level. If the room has a single person capacity, it can not be used for a couple (2 person) massage.
  * Rooms have a set of possible capabilities. i.e. a massage can not be done in a room that only allows manicures.

### Appointment Types
  * Employees have 1-n capabilities and can only do appointments that match the requested appointment.
  * Customers can choose a specific employee.
  * Customers can choose a male or female preference.
  * Customers can choose no preference.

----

## 2. Optimize Scheduling:

### Customer is attempting to schedule an appointment:
  * Customer has chosen desired services.
  * Customer has chosen if they want a specific employee.
  * If customer does not want a specific employee, customer has chosen if they prefer male or female employee.
  * If customer does not choose specific employee or gender preference, customer can receive any employee.
  
### System should suggest a time to the customer that maintains:
  * Room balance.
  * Employee appointment count balance.

  
  * System should be able to re-balance rooms on demand for best case scenario of room utilization.
  * Appointments however can not be rebalanced as they are set times given to customers.
  * Employees doing a given appointment at a given time can be rebalanced if they were not specifically requested by the customer.

  i.e. a suggested appointment time should adhere to customer's requests and preferences, but unless impossible, not suggest a time that gives one employee more appointments than other employees or over schedule one room leaving used significantly more than others.

----

##  Example:

```
  Rooms:
    Room A:
      Capabilities: Swedish Massage, Deep Tissue Massage, Hot Rocks Therapy, Manicure, Pedicure
      Capacity: 2
    Room B:
      Capabilities: Manicure, Pedicure, Facial
      Capacity: 1
    Room C:
      Capabilities: Swedish Massage, Deep Tissue Massage, Hot Rocks Therapy, Manicure, Pedicure, Facial
      Capacity: 2
    Room D:
      Capabilities: Swedish Massage, Deep Tissue Massage, Hot Rocks Therapy, Manicure, Pedicure, Facial
      Capacity: 1
    Room E:
      Capabilities: Swedish Massage, Deep Tissue Massage, Hot Rocks Therapy, Manicure, Pedicure
      Capacity: 2

  Employees:
    Stacy:
      Shifts: M 9-12 / 1-5, Tu 9-12 / 1-5, W 9-12 / 1-5, Th Off, Fr 9-12, Sa 9-12, Su 9-12
      Capabilities: Swedish Massage, Deep Tissue Massage, Facial
    Jessica:
      Shifts: M 9-12 / 1-5, Tu 9-12 / 1-5, W 9-12 / 1-5, Th Off, Fr 9-12, Sa Off, Su Off
      Capabilities: Manicure, Pedicure, Deep Tissue Massage
    John:
      Shifts: M 9-10 / 11-4, Tu 8-11 / 12-3, W Off, Th Off, Fr 9-12, Sa Off, Su Off
      Capabilities: Deep Tissue Massage, Sports Massage, Swedish Massage, Hot Rocks Therapy
    Steve:
      Shifts: M Off, Tu Off, W Off, Th 12-8, Fr 9-12, Sa Off, Su 1-7
      Capabilities: Deep Tissue Massage, Sports Massage, Swedish Massage

  Capabilities:
    Swedish Massage:
      0.5 hours, 1 hours, 1.5 hours
      Couple capable
    Deep Tissue Massage:
      0.5 hours, 1 hours
      Couple capable
    Manicure:
      0.5 hours
    Pedicure:
      0.5 hours
    Hot Rocks Therapy:
      0.5 hours, 1 hours, 1.5 hours
      Couple Capable

  Appointments:
    Via Monte-Carlo, generate optimal appointments.
```