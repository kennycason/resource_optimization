package com.kennycason.ml.model

import org.eclipse.collections.api.list.ListIterable

/**
 * Created by kenny on 11/7/16.
 */
data class Room(val name: String,
                val services: ListIterable<Service>)