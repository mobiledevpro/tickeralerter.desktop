package com.mobiledepro.main.domain.model

data class WalletBalance(
    val asset: String,
    val balance: Double,
    val availableBalance: Double, //= [balance] - [open orders amount]
    val unrealizedPnL: Double,
    val updateTime: Long
) {
    fun listKey(): String = "${asset}_${balance}"

    fun marginBalance(): Double = balance + unrealizedPnL
}

val fakeBalances = listOf(
    WalletBalance(
        asset = "USDT",
        balance = 1234.5645,
        availableBalance = 456.8967,
        unrealizedPnL = 10.0,
        updateTime = 0
    ),

    WalletBalance(
        asset = "BUSD",
        balance = 1234.5645,
        availableBalance = 456.8967,
        unrealizedPnL = -5.32,
        updateTime = 0
    )

)