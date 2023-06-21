package com.mobiledepro.main.domain.model

data class Chart(
    var candleList: List<Candle>
) {
    fun getHigherHighPrice(): Double =
        if (getLimitedCandleList().isNotEmpty()) getLimitedCandleList().sortedByDescending { it.priceHigh }[0].priceHigh else 0.0

    fun getLowerLowPrice(): Double =
        if (getLimitedCandleList().isNotEmpty()) getLimitedCandleList().sortedBy { it.priceLow }[0].priceLow else 0.0

    // Limit it by 180 by default
    fun candlesCount(): Int = getLimitedCandleList().size

    /**
     * Return EMA for every candle
     */
    fun calcEMA(period: Int): List<Double> {
        if (candleList.size < period) return emptyList() //throw RuntimeException("EMA $period cannot be calculated - candle list size ${candleList.size} < $period")
        val emaList = ArrayList<Double>()

        //With smoothing 2.0 EMA equals to TradingView EMA 50
        val smoothingFactor = 2.0 / (period + 1)
        val closePriceList: List<Double> = candleList.mapTo(ArrayList<Double>()) { it.priceClose }
        // Initialize EMA with the average of the first 200 elements

        //First EMA value is just SMA
        var ema: Double = closePriceList.subList(0, period).average()
        emaList.add(ema)

        for (i in period until candleList.size) {
            val currentValue = candleList[i].priceClose
            // ema += (currentValue - ema) * smoothingFactor
            ema = currentValue * smoothingFactor + ema * (1 - smoothingFactor)
            emaList.add(ema)
        }

        return emaList
    }

    fun getLimitedCandleList(): List<Candle> =
        if (candleList.size > CHART_LIMIT_CANDLE_COUNT)
            candleList.takeLast(CHART_LIMIT_CANDLE_COUNT)
        else
            candleList

    companion object {
        const val CHART_LIMIT_CANDLE_COUNT = 180 //show only last 180 candles
    }
}
