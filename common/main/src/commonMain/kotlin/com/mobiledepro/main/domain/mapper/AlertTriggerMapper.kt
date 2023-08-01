package com.mobiledepro.main.domain.mapper

import com.mobiledepro.main.domain.model.*
import com.mobiledevpro.database.AlertTriggerEntry
import java.util.*


fun AlertTriggerEntry.toDomain(): AlertTrigger =
    AlertTrigger(
        timeCreated = timeCreatedAt,
        symbol = symbol,
        timeFrame = timeFrame,
        active = active == 1L,
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
        active = if (active) 1 else 0,
        conditionSource = alertSettings.conditionSource.toStr(),
        conditionType = alertSettings.conditionType.toStr(),
        conditionTarget = alertSettings.conditionTarget.toStr(),
        targetPrice = alertSettings.targetPrice
    )