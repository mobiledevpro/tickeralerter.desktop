package com.mobiledepro.main.util

import java.text.SimpleDateFormat
import java.util.*

fun Long.toTimeDate(format: String): String =
    SimpleDateFormat(format, Locale.getDefault()).format(Date(this))


const val TIME_FORMAT_ALERT_EVENT = "h:mm:ss a, MMM d"