package com.mobiledevpro.tickerlist.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledevpro.ui.accent
import com.mobiledevpro.ui.white

@Composable
fun TickerItem(ticker: Ticker, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        TickerText(
            value = ticker.symbol,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Text(
            text = ticker.details(),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.white,
            maxLines = 1,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colors.white,
                backgroundColor = MaterialTheme.colors.accent
            )
        ) {
            Text(
                text = if (ticker.selected) "Remove" else "Add",
                style = MaterialTheme.typography.button
            )
        }
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