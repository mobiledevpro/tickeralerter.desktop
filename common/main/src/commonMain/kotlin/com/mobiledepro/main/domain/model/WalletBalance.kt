package com.mobiledepro.main.domain.model

data class WalletBalance(
    val asset: String,
    val balance: Double,
    val marginBalance: Double,
    val unrealizedPnL: Double
) {
    fun listKey(): String = "${asset}_${balance}"
}

val fakeBalances = listOf(
    WalletBalance(
        asset = "USDT",
        balance = 1234.5645,
        marginBalance = 456.8967,
        unrealizedPnL = 0.0
    ),

    WalletBalance(
        asset = "BUSD",
        balance = 1234.5645,
        marginBalance = 456.8967,
        unrealizedPnL = 0.0
    )

)