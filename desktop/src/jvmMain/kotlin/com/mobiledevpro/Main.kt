package com.mobiledevpro


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.mobiledepro.main.di.commonModules
import com.mobiledepro.main.ext.injectScope
import com.mobiledevpro.alert.settings.view.vm.AlertSettingsViewModel
import com.mobiledevpro.alert.triggers.view.vm.AlertTriggersViewModel
import com.mobiledevpro.chart.view.vm.ChartViewModel
import com.mobiledevpro.home.view.HomeScreen
import com.mobiledevpro.home.view.vm.HomeScreenViewModel
import com.mobiledevpro.tickerlist.view.vm.TickerListViewModel
import com.mobiledevpro.ui.Theme
import com.mobiledevpro.watchlist.view.vm.WatchlistViewModel
import di.featureModules
import org.koin.core.context.startKoin

@Composable
fun App() {

    val isTestnet = false //TODO: add switcher to UI

    val homeViewModel: HomeScreenViewModel by remember { injectScope() }
    val watchListViewModel: WatchlistViewModel by remember { injectScope() }
    val chartViewModel: ChartViewModel by remember { injectScope() }
    val tickerListViewModel: TickerListViewModel by remember { injectScope() }
    val alertTriggerListViewModel: AlertTriggersViewModel by remember { injectScope() }
    val alertSettingsViewModel: AlertSettingsViewModel by remember { injectScope() }

    Theme {
        HomeScreen(
            homeUIState = homeViewModel.uiState,
            tickerListUIState = tickerListViewModel.uiState,
            watchListUIState = watchListViewModel.uiState,
            chartUIState = chartViewModel.uiState,
            alertTriggerListUIState = alertTriggerListViewModel.uiState,
            alertEventListUIState = homeViewModel.alertEventList,
            alertSettingsUIState = alertSettingsViewModel.uiState,
            onAddToWatchList = watchListViewModel::addToWatchlist,
            onRemoveFromWatchlist = watchListViewModel::removeFromWatchlist,
            onTickerListSearch = tickerListViewModel::tickerListSearch,
            onSelectFromWatchlist = chartViewModel::openChart,
            onAlertSettingsSave = alertSettingsViewModel::onSave,
            onAlertSettingsClose = alertSettingsViewModel::onClose,
            onAlertTriggerAdd = alertSettingsViewModel::onAddNew,
            onAlertTriggerChange = alertSettingsViewModel::onChange
        )
    }
}

fun main() = application {

    startKoin {
        modules(
            commonModules + featureModules
        )
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Ticker Alerter",
        state = rememberWindowState(width = 1280.dp, height = 720.dp)
    ) {
        App()
    }
}
