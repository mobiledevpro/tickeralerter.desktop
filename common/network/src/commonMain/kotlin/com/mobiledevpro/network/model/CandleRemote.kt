package com.mobiledevpro.network.model

import kotlinx.serialization.Serializable

@Serializable
data class CandleRemote(
    val openTime: Long,
    val closeTime: Long,
    val priceOpen: Double,
    val priceClose: Double,
    val priceLow: Double,
    val priceHigh: Double,
    val volume: Double
)
