package feature.main

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import feature.chart.ChartBox
import feature.tickerlist.TickersBox
import feature.tradinglog.TradingLogBox

@Composable
fun MainScreen() {

    val scope = rememberCoroutineScope()
    val viewModel = remember { MainScreenViewModel(scope) }


    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {

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
                    viewModel.tradingLog,
                    modifier = Modifier
                        .border(width = 2.dp, color = Color.Gray)
                        .fillMaxHeight()
                        .fillMaxWidth()
                )

            }
        }


    }
}
