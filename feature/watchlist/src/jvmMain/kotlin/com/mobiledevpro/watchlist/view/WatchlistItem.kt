package com.mobiledevpro.watchlist.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledepro.main.view.ext.getPriceColor
import com.mobiledevpro.ui.white


@Composable
fun WatchlistItem(ticker: Ticker, onRemove: () -> Unit, onSelect: () -> Unit) {
    Row(
        modifier = Modifier.height(32.dp).clickable {
            onSelect()
        },
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TickerText(
            value = ticker.symbol,
            textAlign = TextAlign.Start,
            modifier = Modifier.width(100.dp)
        )
        TickerText(
            textAlign = TextAlign.End,
            value = ticker.lastPrice.toString(),
            modifier = Modifier.width(80.dp)
        )
        TickerText(
            textAlign = TextAlign.End,
            value = ticker.priceChange.toString(),
            color = ticker.getPriceColor(),
            modifier = Modifier.width(80.dp)
        )
        TickerText(
            textAlign = TextAlign.End,
            value = ticker.priceChangePercent.toString(),
            color = ticker.getPriceColor(),
            modifier = Modifier.width(80.dp)
        )

        RemoveButton(
            onClick = onRemove
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

@Composable
fun RemoveButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.padding(start = 16.dp).size(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface.copy(alpha = 0.3F)
        )
    }
}