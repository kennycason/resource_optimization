package com.kennycason.ml.model.generation

import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by kenny on 11/8/16.
 */
class PeopleFactoryTest {

    @Test
    fun poepleGeneration() {
        val peopleFactory = PeopleFactory()
        val people = peopleFactory.build(10)

        assertEquals(10, people.size())
        people.forEach(::println)
    }
}