package com.mobiledevpro.ui

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@get:Composable
val Colors.lightGreen: Color
    get() = Color(0x9932CD32)

@get:Composable
val Colors.red: Color
    get() = Color(0x99F44336)

@get:Composable
val Colors.white: Color
    get() = Color(0x99FFFFFF)


@get:Composable
val Colors.accent: Color
    get() = Color(0x9903DAC6)

@get:Composable
val Colors.orange: Color
    get() = Color(0x99FF9800)


@get:Composable
val Colors.backgroundTransparent: Color
    get() = Color(0xFF121212).copy(alpha = 0.7F)

@get:Composable
val Colors.positiveCandleColor: Color
    get() = Color(0xFF26A69A)

@get:Composable
val Colors.negativeCandleColor: Color
    get() = Color(0xFF9598A2).copy(alpha = 0.9F)

@get:Composable
val Colors.ema200Color: Color
    get() = Color(0xFF1976D2).copy(alpha = 0.9F)

@get:Composable
val Colors.ema50Color: Color
    get() = Color(0xFFFBC02D).copy(alpha = 0.9F)

@Composable
fun Double.getPriceColor(): Color = when {
    this > 0 -> MaterialTheme.colors.lightGreen
    this < 0 -> MaterialTheme.colors.red
    else -> MaterialTheme.colors.white
}
