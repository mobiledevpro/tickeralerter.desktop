package common.data.local.model

data class TickerLocal(
    val symbol: String,
    val lastPrice: Double,
    val priceChange: Double,
    val priceChangePercent: Double
)