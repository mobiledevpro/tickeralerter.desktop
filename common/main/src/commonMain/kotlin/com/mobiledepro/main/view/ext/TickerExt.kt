package com.mobiledepro.main.view.ext

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledevpro.ui.lightGreen
import com.mobiledevpro.ui.red
import com.mobiledevpro.ui.white

@Composable
fun Ticker.getPriceColor(): Color = when {
    this.priceChange > 0 -> MaterialTheme.colors.lightGreen
    this.priceChange < 0 -> MaterialTheme.colors.red
    else -> MaterialTheme.colors.white
}
