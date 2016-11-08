package com.kennycason.ml.model

import org.eclipse.collections.api.list.ListIterable
import java.time.Duration

/**
 * Created by kenny on 11/7/16.
 */
data class Service(val name: String,
                   val possibleDurations: ListIterable<Duration>)