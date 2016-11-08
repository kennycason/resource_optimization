package com.kennycason.ml.model.time

/**
 * Created by kenny on 11/8/16.
 *
 * values are hours in
 */
data class Range(val startHour: Double,
                 val endHour: Double,
                 val duration: Double = endHour - startHour)