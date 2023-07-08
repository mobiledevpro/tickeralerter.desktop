package com.mobiledevpro.feature.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobiledepro.main.domain.model.*
import com.mobiledevpro.alert.settings.view.AlertSettingsDialog
import com.mobiledevpro.alert.settings.view.AlertsBox
import com.mobiledevpro.chart.view.ChartBox
import com.mobiledevpro.chart.view.ChartSettingsBox
import com.mobiledevpro.feature.main.component.OnlineStatus
import com.mobiledevpro.tickerlist.view.TickerListDialog
import com.mobiledevpro.ui.common.modifierMaxHeight
import com.mobiledevpro.ui.common.modifierMaxSize
import com.mobiledevpro.ui.common.modifierMaxWidth
import com.mobiledevpro.watchlist.view.WatchlistBox
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainScreen(
    serverTimeState: StateFlow<Long>,
    tradingLogState: StateFlow<List<String>>,
    tickerListState: StateFlow<List<Ticker>>,
    watchListState: StateFlow<List<Ticker>>,
    chartState: StateFlow<Chart>,
    alertTriggerListState: StateFlow<List<AlertTrigger>>,
    alertEventListState: StateFlow<List<AlertEvent>>,
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
    val alertTriggers by alertTriggerListState.collectAsState()
    val alertEvents by alertEventListState.collectAsState()

    var addToWatchlistDialogVisible by remember { mutableStateOf(false) }
    var addToAlertsDialogVisible by remember { mutableStateOf(false) }
    var chartSetting by remember { mutableStateOf(ChartSettings()) }

    Surface(
        modifier = modifierMaxSize,
        color = MaterialTheme.colors.background
    ) {
        Box(modifier = modifierMaxSize) {
            Row(
                modifier = Modifier
                    .padding(bottom = 24.dp)
            ) {
                Column(modifier = Modifier.width(430.dp)) {
                    WatchlistBox(
                        list = watchList,
                        onClickAdd = {
                            addToWatchlistDialogVisible = true
                        },
                        onClickRemove = onRemoveFromWatchlist,
                        onSelect = onSelectFromWatchlist,
                        modifier = modifierMaxWidth
                            .fillMaxHeight(0.5f)
                    )


                    AlertsBox(
                        alertTriggerList = alertTriggers,
                        alertEventList = alertEvents,
                        modifier = modifierMaxSize,
                        onClickAdd = {
                            addToAlertsDialogVisible = true
                        }
                    )
                }

                Column(
                    modifier = modifierMaxSize
                ) {

                    ChartBox(
                        chart = chart,
                        chartSettings = chartSetting,
                        modifier = modifierMaxWidth
                            .fillMaxHeight(0.7f)
                    )

                    Row {
                        ChartSettingsBox(
                            settings = chartSetting,
                            onChangeSettings = { settings ->
                                chartSetting = settings
                            },
                            modifier = modifierMaxHeight.widthIn(max = 250.dp)
                        )

                        //trading bot box here

                    }


                }
            }

            /*Online status*/
            OnlineStatus(
                modifier = modifierMaxWidth
                    .align(Alignment.BottomCenter)
                    .height(20.dp),
                serverTime = serverTime
            )

        }

        //Show a dialog to add tickers to watchlist
        if (addToWatchlistDialogVisible)
            TickerListDialog(
                list = tickerList,
                onAdd = onAddToWatchList,
                onRemove = onRemoveFromWatchlist,
                onSearch = onTickerListSearch,
                onClose = {
                    //clear ticker list search
                    onTickerListSearch("")
                    addToWatchlistDialogVisible = false
                }
            )

        //Show a dialog to add/update alerts
        if (addToAlertsDialogVisible)
            AlertSettingsDialog(
                onClose = {
                    addToAlertsDialogVisible = false
                },
                onSave = {

                },
                watchList = fakeTickerListFirst()
            )

    }
}
