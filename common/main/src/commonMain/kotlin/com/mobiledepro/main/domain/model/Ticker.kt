package com.mobiledepro.main.domain.model

data class Ticker(
    val symbol: String,
    val baseAsset: String,
    val contractType: String,
    val lastPrice: Double,
    val priceChange: Double,
    val priceChangePercent: Double,
    var selected: Boolean = false
) {
    fun details(): String = "$baseAsset / ${contractType.uppercase()} CONTRACT"
}

fun fakeTickerListFirst() = listOf(
    Ticker(
        "BTCUSDT",
        "BTC",
        "PERPETUAL",
        26903.2,
        96.6,
        0.36
    ),
    Ticker(
        "BNBUSDT",
        "BNB",
        "PERPETUAL",
        309.63,
        0.49,
        0.16
    ),
    Ticker(
        "ETHUSDT",
        "ETH",
        "PERPETUAL",
        1821.49,
        20.63,
        1.15
    ),

    Ticker(
        "ATOMUSDT",
        "ATOM",
        "PERPETUAL",
        10.575,
        -0.165,
        -1.55
    ),

    Ticker(
        "ADAUSDT",
        "ADA",
        "PERPETUAL",
        0.3696,
        -0.0018,
        -0.48
    ),
    Ticker(
        "BTCUSDT",
        "BTC",
        "PERPETUAL",
        26903.2,
        96.6,
        0.36
    ),
    Ticker(
        "BNBUSDT",
        "BNB",
        "PERPETUAL",
        309.63,
        0.49,
        0.16
    ),
    Ticker(
        "ETHUSDT",
        "ETH",
        "PERPETUAL",
        1821.49,
        20.63,
        1.15
    ),

    Ticker(
        "ATOMUSDT",
        "ATOM",
        "PERPETUAL",
        10.575,
        -0.165,
        -1.55
    ),

    Ticker(
        "ADAUSDT",
        "ADA",
        "PERPETUAL",
        0.3696,
        -0.0018,
        -0.48
    )
)
