package com.mobiledevpro.common.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class SymbolRemote(
    val symbol: String,
    val baseAsset: String,
    val quoteAsset: String,
    val contractType: String
)
