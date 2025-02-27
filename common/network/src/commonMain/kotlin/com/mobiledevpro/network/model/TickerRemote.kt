package com.mobiledevpro.network.model

import kotlinx.serialization.Serializable

@Serializable
data class TickerRemote(
    val symbol: String,
    val baseAsset: String,
    val quoteAsset: String,
    val contractType: String
)
