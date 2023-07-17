package com.mobiledepro.main.util

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

fun Long.toTimeDate(format: String): String =
    SimpleDateFormat(format, Locale.getDefault()).format(Date(this))


fun Long.timeToString(): String =
    if (this == 0L) ""
    else
        DateTimeFormatter.ofPattern(DATE_FORMAT, Locale.getDefault())
            .format(
                Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDateTime()
            )

const val DATE_FORMAT = "MMM d, HH:mm:ss"
const val TIME_FORMAT_ALERT_EVENT = "h:mm:ss a, MMM d"