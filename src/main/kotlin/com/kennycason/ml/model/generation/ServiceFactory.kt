package com.kennycason.ml.model.generation

import com.kennycason.ml.model.Service
import com.kennycason.ml.model.load.StringLineLoader
import org.eclipse.collections.api.list.ListIterable
import org.eclipse.collections.api.list.MutableList
import org.eclipse.collections.impl.factory.Lists
import java.util.*

/**
 * Created by kenny on 11/8/16.
 */
class ServiceFactory {
    private val random = Random()
    private val serviceDurationFactory = ServiceDurationFactory()
    private val stringLineLoader = StringLineLoader()
    val serviceNames = stringLineLoader.load("com/kennycason/ml/data/services.list")

    fun build(n: Int): ListIterable<Service> {
        if (n > serviceNames.size()) { throw IllegalStateException("$n service must be <= 4") }

        val services: MutableList<Service> = Lists.mutable.empty()

        (1.. n).forEach { n ->
            services.add(Service(
                    name = serviceNames[random.nextInt(serviceNames.size())],
                    possibleDurations = serviceDurationFactory.build(random.nextInt(4) + 1)))
        }
        return services
    }

}