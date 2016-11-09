package com.kennycason.ml.model.generation

import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by kenny on 11/8/16.
 */
class WeekdayFactoryTest {

    @Test
    fun weekdayGeneration() {
        val weekdayFactory = WeekdayFactory()
        (1..20).forEach {
            println(weekdayFactory.build())
        }
    }
}