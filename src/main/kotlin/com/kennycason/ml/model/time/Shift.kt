package com.kennycason.ml.model.time

/**
 * Created by kenny on 11/7/16.
 */
data class Shift(val weekday: Weekday,
                 val shift1: Range = Range(0.0, 0.0),
                 val shift2: Range = Range(0.0, 0.0))