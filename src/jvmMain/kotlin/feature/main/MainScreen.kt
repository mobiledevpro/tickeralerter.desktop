package feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import common.domain.model.Ticker
import common.util.timeToString
import feature.chart.ChartBox
import feature.tickerlist.view.TickersBox
import feature.tradinglog.TradingLogBox
import kotlinx.coroutines.flow.StateFlow
import ui.lightGreen
import ui.red

@Composable
fun MainScreen(
    serverTimeState: StateFlow<Long>,
    tradingLogState: StateFlow<List<String>>,
    tickerListState: StateFlow<List<Ticker>>
) {

    val tickerList by tickerListState.collectAsState()
    val tradingLog by tradingLogState.collectAsState()
    val serverTime by serverTimeState.collectAsState()

    println("Time state $serverTime")


    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.padding(bottom = 24.dp)) {
                TickersBox(
                    tickerList,
                    modifier = Modifier
                        .fillMaxHeight()
                        .defaultMinSize(minWidth = 300.dp)
                )

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                    ChartBox(
                        modifier = Modifier
                            .fillMaxHeight(0.7f)
                            .fillMaxWidth()
                    )

                    TradingLogBox(
                        tradingLog,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                    )

                }
            }

            /*Online status*/
            Box(
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().height(20.dp)
                    .background(color = if (serverTime > 0) MaterialTheme.colors.lightGreen else MaterialTheme.colors.red)
            ) {
                Text(
                    text = if (serverTime > 0) "Online" else "Offline",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                if (serverTime > 0)
                    Text(
                        text = "Server time: ${serverTime.timeToString()}",
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                    )
            }

        }


    }
}
