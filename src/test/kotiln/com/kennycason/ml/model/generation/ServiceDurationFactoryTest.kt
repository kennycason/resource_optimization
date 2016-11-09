package com.kennycason.ml.model.generation

import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by kenny on 11/8/16.
 */
class ServiceDurationFactoryTest {

    @Test
    fun serviceDurationGeneration() {
        val serviceDurationFactory = ServiceDurationFactory()
        val serviceDurations = serviceDurationFactory.build(2)

        assertEquals(2, serviceDurations.size())
        serviceDurations.forEach(::println)
    }
}