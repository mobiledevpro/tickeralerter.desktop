package com.mobiledevpro.common.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

fun Long.timeToString(): String =
    if (this == 0L) ""
    else
        DateTimeFormatter.ofPattern(DATE_FORMAT, Locale.getDefault())
            .format(
                Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDateTime()
            )

const val DATE_FORMAT = "MMM d, HH:mm:ss";