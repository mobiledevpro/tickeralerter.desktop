package feature.tradinglog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TradingLogBox(state: StateFlow<List<String>>, modifier: Modifier = Modifier) {
    val log: List<String> by state.collectAsState()

    println("log")

    Box(modifier = modifier) {
        LazyColumn {
            items(items = log) { text ->
                println("log $text")
                Text(text = text)
            }
        }
    }
}