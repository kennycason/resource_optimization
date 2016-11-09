package com.kennycason.ml.model.generation

import com.kennycason.ml.model.Gender
import com.kennycason.ml.model.Person
import com.kennycason.ml.model.load.StringLineLoader
import org.eclipse.collections.api.list.ListIterable
import org.eclipse.collections.api.list.MutableList
import org.eclipse.collections.impl.factory.Lists
import java.util.*

/**
 * Created by kenny on 11/8/16.
 */
class PeopleFactory {
    private val random = Random()
    private val stringLineLoader = StringLineLoader()
    val maleNames = stringLineLoader.load("com/kennycason/ml/data/names.male")
    val femaleNames = stringLineLoader.load("com/kennycason/ml/data/names.female")

    fun build(n: Int): ListIterable<Person> {
        val people: MutableList<Person> = Lists.mutable.empty()
        (1.. n).forEach { n ->
            if (random.nextBoolean()) {
                people.add(Person(name = maleNames[random.nextInt(maleNames.size())], gender = Gender.MALE))
            } else {
                people.add(Person(name = femaleNames[random.nextInt(femaleNames.size())], gender = Gender.FEMALE))
            }
        }
        return people
    }

}