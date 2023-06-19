package com.mobiledepro.main.domain.model

data class Chart(
    var candleList: List<Candle>
) {
    fun getHigherHighPrice(): Double =
        if (candleList.isNotEmpty()) candleList.sortedByDescending { it.priceHigh }[0].priceHigh else 0.0

    fun getLowerLowPrice(): Double =
        if (candleList.isNotEmpty()) candleList.sortedBy { it.priceLow }[0].priceLow else 0.0

    fun candlesCount(): Int = candleList.size
}
