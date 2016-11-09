package com.kennycason.ml.model.generation

import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by kenny on 11/8/16.
 */
class ServiceFactoryTest {

    @Test
    fun serviceGeneration() {
        val serviceFactory = ServiceFactory()
        val services = serviceFactory.build(5)

        assertEquals(5, services.size())
        services.forEach(::println)
    }
}