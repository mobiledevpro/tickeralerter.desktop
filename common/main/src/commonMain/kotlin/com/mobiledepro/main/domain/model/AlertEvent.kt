package com.mobiledepro.main.domain.model

data class AlertEvent(
    val timeCreated: Long,
    val message: String,
    val shown: Boolean //true - if event was shown as a pop-up
)
