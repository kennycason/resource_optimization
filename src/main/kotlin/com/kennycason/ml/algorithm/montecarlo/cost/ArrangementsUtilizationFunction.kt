package com.kennycason.ml.algorithm.montecarlo.cost

import com.kennycason.ml.algorithm.montecarlo.model.Arrangement
import org.eclipse.collections.api.RichIterable
import org.eclipse.collections.api.list.ListIterable

/**
 * Created by kenny on 11/13/16.
 */
class ArrangementsUtilizationFunction(private val arrangementUtilizationFunction: ArrangementUtilizationFunction) {

    fun evaluate(arrangements: ListIterable<Arrangement>): Double =
            arrangements.fold(0.0, {sum, arrangement -> sum + arrangementUtilizationFunction.evaluate(arrangement = arrangement)})
                        .div(arrangements.size())

}