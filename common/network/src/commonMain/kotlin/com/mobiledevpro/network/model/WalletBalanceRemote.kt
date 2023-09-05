package com.mobiledevpro.network.model

import kotlinx.serialization.Serializable

@Serializable
data class WalletBalanceRemote(
    val asset: String,
    val balance: Double,
    val availableBalance: Double,
    val unrealizedPnL: Double,
    val updateTime: Long
)
