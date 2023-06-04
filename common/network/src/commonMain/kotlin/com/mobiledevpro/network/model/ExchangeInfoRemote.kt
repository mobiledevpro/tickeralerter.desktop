package com.mobiledevpro.common.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ExchangeInfoRemote(
    val symbols: List<SymbolRemote>
)
