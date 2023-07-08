package com.mobiledepro.main.domain.model

data class AlertTrigger(
    val timeCreated: Long,
    val symbol: String,
    val timeFrame: String,
    val alertCondition: AlertCondition,
    val active: Boolean
) {
    fun title(): String = "${source()} ${alertCondition.conditionType.toStr()} ${target()}"

    private fun source() = when (alertCondition.conditionSource) {
        ConditionSource.TICKER_PRICE -> "$symbol ($timeFrame)"
        else -> alertCondition.conditionSource.toStr()
    }

    private fun target() = when (alertCondition.conditionTarget) {
        ConditionTarget.PRICE -> "${alertCondition.targetPrice}"
        else -> alertCondition.conditionTarget.toStr()
    }
}


fun fakeAlertTriggersList(): List<AlertTrigger> = listOf(
    AlertTrigger(
        1688180400000,
        "BTCUSDT",
        "1h",
        AlertCondition(
            conditionSource = ConditionSource.TICKER_PRICE,
            conditionType = ConditionType.CROSSING_UP,
            conditionTarget = ConditionTarget.PRICE,
            targetPrice = 33_000.00,
            symbol = "BTCUSDT"
        ),
        active = true
    ),

    AlertTrigger(
        1688182200000,
        "ETHUSDT",
        "1h",
        AlertCondition(
            conditionSource = ConditionSource.TICKER_PRICE,
            conditionType = ConditionType.CROSSING_DOWN,
            conditionTarget = ConditionTarget.PRICE,
            targetPrice = 1_900.00,
            symbol = "ETHUSDT"
        ),
        active = true
    ),

    AlertTrigger(
        1688182500000,
        "AAVEUSDT",
        "5m",
        AlertCondition(
            conditionSource = ConditionSource.EMA_200,
            conditionType = ConditionType.CROSSING,
            conditionTarget = ConditionTarget.EMA_50,
            symbol = "AAVEUSDT"
        ),
        active = false
    ),
    AlertTrigger(
        1688182800000,
        "WOOUSDT",
        "15m",
        AlertCondition(
            conditionSource = ConditionSource.TICKER_PRICE,
            conditionType = ConditionType.CROSSING_DOWN,
            conditionTarget = ConditionTarget.EMA_RIBBON,
            symbol = "WOOUSDT"
        ),
        active = true
    )
)