package com.mobiledevpro.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountWSResponse(
    @SerialName("result")
    val result: List<Result>
) {
    fun getBalances(): List<AccountWSBalanceRemote> =
        result.find { result -> result.request.contains("balance") }?.account?.balances
            ?: emptyList()
}

@Serializable
data class Result(
    @SerialName("req")
    val request: String,
    @SerialName("res")
    val account: AccountWSRemote? = null
)

@Serializable
data class AccountWSRemote(
    @SerialName("balances")
    val balances: List<AccountWSBalanceRemote>? = null // this field should be  optional
)

@Serializable
data class AccountWSBalanceRemote(
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

