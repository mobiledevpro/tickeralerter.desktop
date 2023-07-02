package com.mobiledepro.main.domain.model

data class AlertCondition(
    val conditionSource: ConditionSource,
    val conditionType: ConditionType,
    val conditionTarget: ConditionTarget,
    val targetValue: Double? = null
)

enum class ConditionSource {
    TICKER_PRICE,
    EMA_50,
    EMA_200
}

enum class ConditionType {
    CROSSING,
    CROSSING_UP,
    CROSSING_DOWN
}

enum class ConditionTarget {
    PRICE,
    EMA_50,
    EMA_200,
    EMA_RIBBON
}

fun ConditionSource.toStr() = when (this) {
    ConditionSource.EMA_50 -> "EMA 50"
    ConditionSource.EMA_200 -> "EMA 200"
    else -> this
}

fun ConditionType.toStr() = when (this) {
    ConditionType.CROSSING -> "Crossing Up"
    ConditionType.CROSSING_DOWN -> "Crossing Down"
    ConditionType.CROSSING_UP -> "Crossing Up"
}

fun ConditionTarget.toStr() = when (this) {
    ConditionTarget.EMA_50 -> "EMA 50"
    ConditionTarget.EMA_200 -> "EMA 200"
    ConditionTarget.EMA_RIBBON -> "EMA Ribbon"
    else -> this
}