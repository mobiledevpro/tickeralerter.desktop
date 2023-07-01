package com.mobiledevpro.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mobiledepro.main.domain.model.Chart
import com.mobiledepro.main.domain.model.ChartSettings
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledevpro.alert.settings.view.AlertsBox
import com.mobiledevpro.chart.view.ChartBox
import com.mobiledevpro.chart.view.ChartSettingsBox
import com.mobiledevpro.common.util.timeToString
import com.mobiledevpro.tickerlist.view.TickerListSurface
import com.mobiledevpro.ui.lightGreen
import com.mobiledevpro.ui.red
import com.mobiledevpro.watchlist.view.WatchlistBox
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainScreen(
    serverTimeState: StateFlow<Long>,
    tradingLogState: StateFlow<List<String>>,
    tickerListState: StateFlow<List<Ticker>>,
    watchListState: StateFlow<List<Ticker>>,
    chartState: StateFlow<Chart>,
    onAddToWatchList: (Ticker) -> Unit,
    onRemoveFromWatchlist: (Ticker) -> Unit,
    onSelectFromWatchlist: (Ticker) -> Unit,
    onTickerListSearch: (String) -> Unit
) {

    val watchList by watchListState.collectAsState()
    val tickerList by tickerListState.collectAsState()
    val tradingLog by tradingLogState.collectAsState()
    val serverTime by serverTimeState.collectAsState()
    val chart by chartState.collectAsState()

    val tickerListDialogVisible = remember { mutableStateOf(false) }
    val chartSetting = remember { mutableStateOf(ChartSettings()) }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            Row(
                modifier = Modifier
                    .padding(bottom = 24.dp)
            ) {
                Column(modifier = Modifier.width(430.dp)) {
                    WatchlistBox(
                        list = watchList,
                        onClickAdd = {
                            tickerListDialogVisible.value = true
                        },
                        onClickRemove = onRemoveFromWatchlist,
                        onSelect = onSelectFromWatchlist,
                        modifier = Modifier
                            .fillMaxHeight(0.5f)
                            .fillMaxWidth()
                    )


                    AlertsBox(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        onClickAdd = { /*Show dialog to add new alert*/ }
                    )
                }

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                    ChartBox(
                        chart = chart,
                        chartSettings = chartSetting.value,
                        modifier = Modifier
                            .fillMaxHeight(0.7f)
                            .fillMaxWidth()
                    )

                    Row {
                        ChartSettingsBox(
                            settings = chartSetting.value,
                            onChangeSettings = { settings ->
                                chartSetting.value = settings
                            },
                            modifier = Modifier.widthIn(max = 250.dp).fillMaxHeight()
                        )

                        //trading bot box here

                    }


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

        //Show a dialog to add tickers to watchlist
        if (tickerListDialogVisible.value)
            TickerListSurface(
                list = tickerList,
                onAdd = onAddToWatchList,
                onRemove = onRemoveFromWatchlist,
                onSearch = onTickerListSearch,
                onClose = {
                    //clear ticker list search
                    onTickerListSearch("")
                    tickerListDialogVisible.value = false
                }
            )
    }
}
