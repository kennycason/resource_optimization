package com.kennycason.ml.model.load

import org.apache.commons.io.IOUtils
import org.eclipse.collections.api.list.ListIterable
import org.eclipse.collections.impl.list.mutable.ListAdapter

/**
 * Created by kenny on 11/8/16.
 */
class StringLineLoader {
    fun load(resource: String) : ListIterable<String> {
        val ioStream = Thread.currentThread()
                .contextClassLoader
                .getResourceAsStream(resource)

        return ListAdapter
                .adapt(IOUtils.readLines(ioStream))
                .select { s -> s.isNotBlank() }
                .collect { s -> s.trim() }
                .toList()
    }

}