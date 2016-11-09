package com.kennycason.ml.model.generation

import com.kennycason.ml.model.Room
import com.kennycason.ml.model.load.StringLineLoader
import org.eclipse.collections.api.list.ListIterable
import org.eclipse.collections.api.list.MutableList
import org.eclipse.collections.impl.factory.Lists
import java.util.*

/**
 * Created by kenny on 11/8/16.
 */
class RoomFactory {
    private val random = Random()
    private val serviceFactory = ServiceFactory()
    private val stringLineLoader = StringLineLoader()
    private val roomNames = stringLineLoader.load("com/kennycason/ml/data/rooms.list")

    fun build(n: Int): ListIterable<Room> {
        val rooms: MutableList<Room> = Lists.mutable.empty()

        if (n > roomNames.size()) { throw IllegalStateException("$n rooms must be < ${roomNames.size()}") }

        (1.. n).forEach {
            rooms.add(Room(
                    name = roomNames[n - 1],
                    services = serviceFactory.build(random.nextInt(3) + 1)))
        }

        return rooms
    }

}