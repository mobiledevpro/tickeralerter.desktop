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
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledevpro.chart.view.ChartBox
import com.mobiledevpro.common.util.timeToString
import com.mobiledevpro.feature.tradinglog.TradingLogBox
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
    onAddToWatchList: (Ticker) -> Unit,
    onRemoveFromWatchlist: (Ticker) -> Unit,
    onTickerListSearch: (String) -> Unit
) {

    val watchList by watchListState.collectAsState()
    val tickerList by tickerListState.collectAsState()
    val tradingLog by tradingLogState.collectAsState()
    val serverTime by serverTimeState.collectAsState()

    println("Time state $serverTime")

    val tickerListDialogVisible = remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.padding(bottom = 24.dp)) {
                WatchlistBox(
                    list = watchList,
                    onClickAdd = {
                        tickerListDialogVisible.value = true
                    },
                    onClickRemove = onRemoveFromWatchlist,
                    modifier = Modifier
                        .fillMaxHeight()
                        .widthIn(min = 400.dp, max = 450.dp)
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
