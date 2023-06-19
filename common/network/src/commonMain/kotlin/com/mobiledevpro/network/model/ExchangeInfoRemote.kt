package com.mobiledevpro.common.data.remote.model

import com.mobiledevpro.network.model.TickerRemote
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeInfoRemote(
    val symbols: List<TickerRemote>
)
