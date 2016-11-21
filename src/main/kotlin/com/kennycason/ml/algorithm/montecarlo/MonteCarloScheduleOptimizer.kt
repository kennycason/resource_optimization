package com.kennycason.ml.algorithm.montecarlo

import com.kennycason.ml.algorithm.montecarlo.model.Arrangement
import com.kennycason.ml.model.AppointmentRequest
import com.kennycason.ml.model.Office
import org.eclipse.collections.api.RichIterable
import org.eclipse.collections.api.list.ListIterable
import org.eclipse.collections.impl.factory.Lists

/**
 * Created by kenny on 11/13/16.
 *
 * This class
 */
class MonteCarloScheduleOptimizer(val office: Office) {

    fun balance(appointmentRequests: ListIterable<AppointmentRequest>) : ListIterable<Arrangement> {
        return Lists.mutable.empty()
    }

}