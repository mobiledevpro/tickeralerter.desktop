package com.mobiledepro.main.domain.model

data class AlertCondition(
    var symbol: String,
    val conditionSource: ConditionSource = ConditionSource.TICKER_PRICE,
    var conditionType: ConditionType = ConditionType.CROSSING,
    var conditionTarget: ConditionTarget = ConditionTarget.PRICE,
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
    ConditionType.CROSSING -> "Crossing"
    ConditionType.CROSSING_DOWN -> "Crossing Down"
    ConditionType.CROSSING_UP -> "Crossing Up"
    else -> this.toString()
}

fun ConditionTarget.toStr() = when (this) {
    ConditionTarget.PRICE -> "Price"
    ConditionTarget.EMA_50 -> "EMA 50"
    ConditionTarget.EMA_200 -> "EMA 200"
    ConditionTarget.EMA_RIBBON -> "EMA Ribbon"
    else -> this.toString()
}

fun String.toConditionType(): ConditionType = when (this) {
    "Crossing Up" -> ConditionType.CROSSING_UP
    "Crossing Down" -> ConditionType.CROSSING_DOWN
    "Crossing" -> ConditionType.CROSSING
    else -> throw RuntimeException("Cannot convert '$this' to condition type")
}

fun String.toConditionTarget(): ConditionTarget = when (this) {
    "Price" -> ConditionTarget.PRICE
    "EMA 50" -> ConditionTarget.EMA_50
    "EMA 200" -> ConditionTarget.EMA_200
    "EMA Ribbon" -> ConditionTarget.EMA_RIBBON
    else -> throw RuntimeException("Cannot convert '$this' to condition target")
}

fun conditionTypeList(): List<String> =
    ConditionType.values().toList()
        .mapTo(ArrayList<String>(), ConditionType::toStr)

fun conditionTargetList(): List<String> =
    ConditionTarget.values().toList()
        .mapTo(ArrayList<String>(), ConditionTarget::toStr)