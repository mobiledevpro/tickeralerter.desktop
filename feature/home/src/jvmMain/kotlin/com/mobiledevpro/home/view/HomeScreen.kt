package com.mobiledevpro.home.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobiledepro.main.domain.model.AlertEvent
import com.mobiledepro.main.domain.model.AlertTrigger
import com.mobiledepro.main.domain.model.ChartSettings
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledevpro.alert.settings.view.AlertSettingsDialog
import com.mobiledevpro.alert.settings.view.state.AlertSettingsUIState
import com.mobiledevpro.alert.triggers.view.state.AlertTriggersUIState
import com.mobiledevpro.alerts.view.AlertsBox
import com.mobiledevpro.chart.view.ChartBox
import com.mobiledevpro.chart.view.ChartSettingsBox
import com.mobiledevpro.chart.view.state.ChartUIState
import com.mobiledevpro.home.view.component.OnlineStatus
import com.mobiledevpro.home.view.state.HomeUIState
import com.mobiledevpro.orders.view.OrdersBox
import com.mobiledevpro.tickerlist.view.TickerListDialog
import com.mobiledevpro.tickerlist.view.state.TickerListUIState
import com.mobiledevpro.ui.common.modifierMaxHeight
import com.mobiledevpro.ui.common.modifierMaxSize
import com.mobiledevpro.ui.common.modifierMaxWidth
import com.mobiledevpro.watchlist.view.WatchlistBox
import com.mobiledevpro.watchlist.view.state.WatchlistUIState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeScreen(
    homeUIState: StateFlow<HomeUIState>,
    tickerListUIState: StateFlow<TickerListUIState>,
    watchListUIState: StateFlow<WatchlistUIState>,
    chartUIState: StateFlow<ChartUIState>,
    alertTriggerListUIState: StateFlow<AlertTriggersUIState>,
    alertEventListUIState: StateFlow<List<AlertEvent>>,
    alertSettingsUIState: StateFlow<AlertSettingsUIState>,
    onAddToWatchList: (Ticker) -> Unit,
    onRemoveFromWatchlist: (Ticker) -> Unit,
    onSelectFromWatchlist: (Ticker) -> Unit,
    onTickerListSearch: (String) -> Unit,
    onAlertSettingsSave: () -> Unit,
    onAlertSettingsClose: () -> Unit,
    onAlertTriggerAdd: () -> Unit,
    onAlertTriggerEdit: (AlertTrigger) -> Unit,
    onAlertTriggerChanged: (AlertTrigger) -> Unit,
    onAlertTriggerChangeStatus: (AlertTrigger) -> Unit
) {

    val homeState by homeUIState.collectAsState()
    val watchListState by watchListUIState.collectAsState()
    val tickerListState by tickerListUIState.collectAsState()
    val chartState by chartUIState.collectAsState()
    val alertTriggersState by alertTriggerListUIState.collectAsState()
    val alertEventsState by alertEventListUIState.collectAsState()
    val alertSettingsState by alertSettingsUIState.collectAsState()

    var addToWatchlistDialogVisible by remember { mutableStateOf(false) }
    var chartSetting by remember { mutableStateOf(ChartSettings()) }

    println(":collect as state: ")

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
                        state = watchListState,
                        onClickAdd = {
                            addToWatchlistDialogVisible = true
                        },
                        onClickRemove = onRemoveFromWatchlist,
                        onSelect = onSelectFromWatchlist,
                        modifier = modifierMaxWidth
                            .fillMaxHeight(0.5f)
                    )

                    AlertsBox(
                        alertTriggersState = alertTriggersState,
                        alertEventList = alertEventsState,
                        modifier = modifierMaxSize,
                        onClickAdd = onAlertTriggerAdd,
                        onClickEdit = onAlertTriggerEdit,
                        onChangeStatus = onAlertTriggerChangeStatus
                    )
                }

                Column(
                    modifier = modifierMaxSize
                ) {

                    ChartBox(
                        state = chartState,
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

                        //Orders
                        OrdersBox(modifier = modifierMaxSize)

                    }


                }
            }

            /*Online status*/
            OnlineStatus(
                modifier = modifierMaxWidth
                    .align(Alignment.BottomCenter)
                    .height(20.dp),
                serverTime = when (homeState) {
                    is HomeUIState.Success -> (homeState as HomeUIState.Success).serverTimeMs
                    else -> 0L
                }
            )
        }

        //Show a dialog to add tickers to watchlist
        if (addToWatchlistDialogVisible)
            TickerListDialog(
                state = tickerListState,
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
        if (alertSettingsState is AlertSettingsUIState.Visible) {
            AlertSettingsDialog(
                state = alertSettingsState,
                onChanged = onAlertTriggerChanged,
                onClose = onAlertSettingsClose,
                onSave = onAlertSettingsSave
            )
        }

    }
}
