package com.kennycason.ml.model

import com.kennycason.ml.model.time.Range

/**
 * Created by kenny on 11/20/16.
 */
data class AssignmentResult(val success: Boolean,
                            val time: Range? = null)