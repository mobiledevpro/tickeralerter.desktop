package com.mobiledepro.main.domain.model

data class Candle(
    val openTime: Long,
    val closeTime: Long,
    val priceOpen: Double,
    val priceClose: Double,
    val priceLow: Double,
    val priceHigh: Double,
    val volume: Double
)
