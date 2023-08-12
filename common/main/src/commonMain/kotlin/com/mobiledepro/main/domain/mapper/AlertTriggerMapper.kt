package com.mobiledepro.main.domain.mapper

import com.mobiledepro.main.domain.model.*
import com.mobiledevpro.database.AlertTriggerEntry
import java.util.*


fun AlertTriggerEntry.toDomain(): AlertTrigger =
    AlertTrigger(
        timeCreated = timeCreatedAt,
        symbol = symbol,
        timeFrame = timeFrame,
        status = AlertStatus.valueOf(status),
        frequency = AlertFrequency.valueOf(frequency),
        alertSettings = AlertSettings(
            conditionSource = conditionSource.toConditionSource(),
            conditionType = conditionType.toConditionType(),
            conditionTarget = conditionTarget.toConditionTarget(),
            targetPrice = targetPrice
        )
    )

fun AlertTrigger.toLocal(): AlertTriggerEntry =
    AlertTriggerEntry(
        timeCreatedAt = timeCreated ?: Date().time,
        symbol = symbol,
        timeFrame = timeFrame,
        status = status.name,
        frequency = frequency.name,
        conditionSource = alertSettings.conditionSource.toStr(),
        conditionType = alertSettings.conditionType.toStr(),
        conditionTarget = alertSettings.conditionTarget.toStr(),
        targetPrice = alertSettings.targetPrice
    )