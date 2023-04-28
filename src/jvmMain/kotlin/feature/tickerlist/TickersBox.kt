package feature.tickerlist

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun TickersBox(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text("Tickers here")
    }
}