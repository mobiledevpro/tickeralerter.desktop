package feature.tradinglog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TradingLogBox(log: List<String>, modifier: Modifier = Modifier) {
    println("log")

    Box(modifier = modifier) {
        LazyColumn {
            items(items = log) { text ->
                println("log $text")
                Text(text = text, style = MaterialTheme.typography.caption)
            }
        }
    }
}