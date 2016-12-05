package com.kennycason.ml.model.time

import com.kennycason.ml.model.Room
import com.kennycason.ml.model.time.Range

/**
 * Created by kenny on 11/20/16.
 */
data class RoomAssignmentResult(val success: Boolean,
                                val time: Range? = null)