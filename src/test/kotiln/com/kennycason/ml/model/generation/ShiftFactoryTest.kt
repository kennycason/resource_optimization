package com.kennycason.ml.model.generation

import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by kenny on 11/8/16.
 */
class ShiftFactoryTest {

    @Test
    fun shiftGeneration() {
        val shiftFactory = ShiftFactory()

        (1..10).forEach {
            for (shift in shiftFactory.build()) {
                println(shift)
            }
        }
    }
}