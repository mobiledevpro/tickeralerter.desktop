package com.mobiledevpro.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalletBalanceRemote(
    @SerialName("asset")
    val asset: String,
    @SerialName("balance")
    val balance: Double,
    @SerialName("availableBalance")
    val availableBalance: Double,
    @SerialName("crossUnPnl")
    val unrealizedPnL: Double,
    @SerialName("updateTime")
    val updateTime: Long
)
