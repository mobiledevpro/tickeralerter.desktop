package com.mobiledevpro.feature.tradinglog

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobiledevpro.ui.component.WidgetBox

@Composable
fun TradingLogBox(log: List<String>, modifier: Modifier = Modifier) {

    WidgetBox(modifier = modifier) {
        LazyColumn {
            items(items = log) { text ->
                println("log $text")
                Text(text = text, style = MaterialTheme.typography.caption)
            }
        }
    }
}