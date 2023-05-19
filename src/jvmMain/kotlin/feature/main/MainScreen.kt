package feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import feature.chart.ChartBox
import feature.tickerlist.TickersBox
import feature.tradinglog.TradingLogBox
import kotlinx.coroutines.flow.StateFlow
import ui.lightGreen
import ui.red

@Composable
fun MainScreen(onlineState: StateFlow<Boolean>, tradingLogState: StateFlow<List<String>>) {

    val tradingLog by tradingLogState.collectAsState()
    val online by onlineState.collectAsState()


    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row {
                TickersBox(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(200.dp)
                        .border(width = 2.dp, color = Color.Gray)
                )

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                    ChartBox(
                        modifier = Modifier
                            .border(width = 2.dp, color = Color.Gray)
                            .fillMaxHeight(0.5f)
                            .fillMaxWidth()
                    )

                    TradingLogBox(
                        tradingLog,
                        modifier = Modifier
                            .border(width = 2.dp, color = Color.Gray)
                            .fillMaxHeight()
                            .fillMaxWidth()
                    )

                }
            }

            /*Online status*/
            Box(
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().height(20.dp)
                    .background(color = if (online) MaterialTheme.colors.lightGreen else MaterialTheme.colors.red)
            ) {
                Text(
                    text = if (online) "Online" else "Offline",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }


    }
}
