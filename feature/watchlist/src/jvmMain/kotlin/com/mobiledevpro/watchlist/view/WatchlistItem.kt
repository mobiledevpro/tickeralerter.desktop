package com.mobiledevpro.watchlist.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledepro.main.view.ext.getPriceColor
import com.mobiledevpro.ui.white


@Composable
fun WatchlistItem(ticker: Ticker) {
    Row(modifier = Modifier.height(24.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
        TickerText(
            value = ticker.symbol,
            textAlign = TextAlign.Start,
            modifier = Modifier.width(120.dp)
        )
        TickerText(
            value = ticker.lastPrice.toString(),
            modifier = Modifier.width(80.dp)
        )
        TickerText(
            value = ticker.priceChange.toString(),
            color = ticker.getPriceColor(),
            modifier = Modifier.width(80.dp)
        )
        TickerText(
            value = ticker.priceChangePercent.toString(),
            color = ticker.getPriceColor(),
            modifier = Modifier.width(80.dp)
        )
    }
}

@Composable
fun TickerText(
    value: String,
    textAlign: TextAlign = TextAlign.End,
    color: Color = MaterialTheme.colors.white,
    modifier: Modifier
) {
    Text(
        text = value,
        style = MaterialTheme.typography.body2,
        textAlign = textAlign,
        color = color,
        modifier = modifier
    )
}