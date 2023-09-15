package com.mobiledepro.main.domain.model

import kotlin.system.measureTimeMillis

data class Chart(
    var candleList: List<Candle>
) {
    fun getHigherHighPrice(): Double =
        if (getLimitedCandleList().isNotEmpty()) getLimitedCandleList().sortedByDescending { it.priceHigh }[0].priceHigh else 0.0

    fun getLowerLowPrice(): Double =
        if (getLimitedCandleList().isNotEmpty()) getLimitedCandleList().sortedBy { it.priceLow }[0].priceLow else 0.0

    // Limit it by 180 by default
    fun candlesCount(): Int = getLimitedCandleList().size

    fun getLimitedCandleList(): List<Candle> =
        if (candleList.size > CHART_LIMIT_CANDLE_COUNT)
            candleList.takeLast(CHART_LIMIT_CANDLE_COUNT)
        else
            candleList

    companion object {
        const val CHART_LIMIT_CANDLE_COUNT = 90 //show only last 90 candles
    }
}

/**
 * Return EMA for every candle
 */

fun List<Candle>.toEMAPrice(period: Int): List<Double> {
    val emaList = ArrayList<Double>()

    measureTimeMillis {
        if (size < period) return@measureTimeMillis //throw RuntimeException("EMA $period cannot be calculated - candle list size ${candleList.size} < $period")

        //With smoothing 2.0 EMA equals to TradingView EMA 50
        val smoothingFactor = 2.0 / (period + 1)
        val closePriceList: List<Double> = mapTo(ArrayList<Double>()) { it.priceClose }
        // Initialize EMA with the average of the first 200 elements

        //First EMA value is just SMA
        var ema: Double = closePriceList.subList(0, period).average()
        emaList.add(ema)

        //EMA for the first N candles is 0 cause there is no enough data to calculate it.
        for (i in 0 until period)
            emaList.add(0.0)

        // Skip the first N candles and start calculation EMA.
        // If period = 50 , start calculating from 51st candle
        for (i in period until size) {
            val currentValue = this[i].priceClose
            // ema += (currentValue - ema) * smoothingFactor
            ema = currentValue * smoothingFactor + ema * (1 - smoothingFactor)
            emaList.add(ema)
        }
    }.also { timeMs ->
        println("::Convert to EMA $period. Time, ms: $timeMs")
    }

    return emaList.takeLast(Chart.CHART_LIMIT_CANDLE_COUNT)
}