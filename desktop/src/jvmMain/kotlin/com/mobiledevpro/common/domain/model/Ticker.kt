package com.mobiledevpro.common.domain.model

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.mobiledevpro.ui.lightGreen
import com.mobiledevpro.ui.red
import com.mobiledevpro.ui.white

data class Ticker(
    val symbol: String,
    val lastPrice: Double,
    val priceChange: Double,
    val priceChangePercent: Double
)

@Composable
fun Ticker.getPriceColor(): Color = when {
    priceChange > 0 -> MaterialTheme.colors.lightGreen
    priceChange < 0 -> MaterialTheme.colors.red
    else -> MaterialTheme.colors.white
}


fun fakeTickerListFirst() = listOf(
    Ticker(
        "BTCUSDT",
        26903.2,
        96.6,
        0.36
    ),
    Ticker(
        "BNBUSDT",
        309.63,
        0.49,
        0.16
    ),
    Ticker(
        "ETHUSDT",
        1821.49,
        20.63,
        1.15
    ),

    Ticker(
        "ATOMUSDT",
        10.575,
        -0.165,
        -1.55
    ),

    Ticker(
        "ADAUSDT",
        0.3696,
        -0.0018,
        -0.48
    )
)

fun fakeTickerListSecond() = listOf(
    Ticker(
        "BTCUSDT",
        26903.5,
        96.8,
        0.38
    ),
    Ticker(
        "BNBUSDT",
        310.63,
        0.50,
        0.17
    ),
    Ticker(
        "ETHUSDT",
        1818.19,
        20.00,
        1.00
    ),

    Ticker(
        "ATOMUSDT",
        10.560,
        -0.170,
        -1.60
    ),

    Ticker(
        "ADAUSDT",
        0.3697,
        -0.0018,
        -0.48
    )
)