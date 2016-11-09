package com.kennycason.ml.model.load

import org.junit.Test

/**
 * Created by kenny on 11/8/16.
 */
class StringLineLoaderTest {

    @Test
    fun loadMaleNames() {
        val stringLineLoader = StringLineLoader()
        stringLineLoader.load("com/kennycason/ml/data/names.male")
                        .forEach(::println)
    }
}