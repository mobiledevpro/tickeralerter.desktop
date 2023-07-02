package com.mobiledepro.main.domain.model

import com.mobiledepro.main.util.TIME_FORMAT_ALERT_EVENT
import com.mobiledepro.main.util.toTimeDate

data class AlertEvent(
    val timeCreated: Long,
    val message: String,
    val shown: Boolean //true - if event was shown as a pop-up
) {
    fun getTimeStr(): String = timeCreated.toTimeDate(TIME_FORMAT_ALERT_EVENT)
}


fun fakeAlertEventsList(): List<AlertEvent> = listOf(
    AlertEvent(
        1688180400000,
        "WOOUSDT (15m) crossed down EMA Ribbon",
        false
    ),

    AlertEvent(
        1688182200000,
        "ETHUSDT (15m) crossed down 1900.00",
        false
    ),
)