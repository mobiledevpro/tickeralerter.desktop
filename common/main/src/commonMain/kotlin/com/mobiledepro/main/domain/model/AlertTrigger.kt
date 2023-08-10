package com.mobiledepro.main.domain.model

data class AlertTrigger(
    val timeCreated: Long? = null,
    var symbol: String,
    val timeFrame: String? = null,
    var alertSettings: AlertSettings = AlertSettings(),
    var active: Boolean = false
) {
    fun title(): String = "${source()} ${alertSettings.conditionType.toStr()} ${target()}"

    fun updateTargetPrice(price: Double?) = apply {
        alertSettings = alertSettings.apply { targetPrice = price }
    }

    fun updateConditionType(type: ConditionType) = apply {
        alertSettings = alertSettings.apply { conditionType = type }
    }

    fun updateConditionTarget(target: ConditionTarget) = apply {
        alertSettings = alertSettings.apply { conditionTarget = target }
    }

    fun updateSymbol(newSymbol: String) = apply {
        symbol = newSymbol
    }

    fun saveEnabled(): Boolean = if (alertSettings.conditionTarget == ConditionTarget.PRICE)
        (alertSettings.targetPrice ?: 0.0) > 0.0
    else
        true

    fun isNew(): Boolean = timeCreated?.let { it == 0L } ?: true

    private fun source() = when (alertSettings.conditionSource) {
        ConditionSource.TICKER_PRICE -> symbol + (timeFrame?.let { " (${timeFrame})" } ?: "")
        else -> alertSettings.conditionSource.toStr()
    }

    private fun target() = when (alertSettings.conditionTarget) {
        ConditionTarget.PRICE -> "${alertSettings.targetPrice}"
        else -> alertSettings.conditionTarget.toStr()
    }
}


fun fakeAlertTriggersList(): List<AlertTrigger> = listOf(
    AlertTrigger(
        1688180400000,
        "BTCUSDT",
        "1h",
        AlertSettings(
            conditionSource = ConditionSource.TICKER_PRICE,
            conditionType = ConditionType.CROSSING_UP,
            conditionTarget = ConditionTarget.PRICE,
            targetPrice = 33_000.00
        ),
        active = true
    ),

    AlertTrigger(
        1688182200000,
        "ETHUSDT",
        "1h",
        AlertSettings(
            conditionSource = ConditionSource.TICKER_PRICE,
            conditionType = ConditionType.CROSSING_DOWN,
            conditionTarget = ConditionTarget.PRICE,
            targetPrice = 1_900.00
        ),
        active = true
    ),

    AlertTrigger(
        1688182500000,
        "AAVEUSDT",
        "5m",
        AlertSettings(
            conditionSource = ConditionSource.EMA_200,
            conditionType = ConditionType.CROSSING,
            conditionTarget = ConditionTarget.EMA_50,
        ),
        active = false
    ),
    AlertTrigger(
        1688182800000,
        "WOOUSDT",
        "15m",
        AlertSettings(
            conditionSource = ConditionSource.TICKER_PRICE,
            conditionType = ConditionType.CROSSING_DOWN,
            conditionTarget = ConditionTarget.EMA_RIBBON,
        ),
        active = true
    )
)