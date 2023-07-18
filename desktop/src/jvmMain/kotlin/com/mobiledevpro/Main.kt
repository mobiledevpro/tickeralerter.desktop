package com.mobiledevpro


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.mobiledepro.main.di.commonModules
import com.mobiledepro.main.ext.injectScope
import com.mobiledevpro.chart.view.vm.ChartViewModel
import com.mobiledevpro.database.AppDatabase
import com.mobiledevpro.home.domain.interactor.HomeScreenInteractor
import com.mobiledevpro.home.domain.interactor.ImplHomeScreenInteractor
import com.mobiledevpro.home.view.HomeScreen
import com.mobiledevpro.home.view.vm.HomeScreenViewModel
import com.mobiledevpro.network.SocketClient
import com.mobiledevpro.tickerlist.data.repository.ImplTickerListRepository
import com.mobiledevpro.tickerlist.data.repository.TickerRepository
import com.mobiledevpro.ui.Theme
import com.mobiledevpro.watchlist.data.repository.ImplWatchListRepository
import com.mobiledevpro.watchlist.data.repository.WatchListRepository
import com.mobiledevpro.watchlist.view.vm.WatchlistViewModel
import di.featureModules
import io.ktor.client.*
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

@Composable
fun App() {

    val isTestnet = false //TODO: add switcher to UI
    val koin: Koin = remember { getKoin() }

    val database: AppDatabase by remember { koin.inject<AppDatabase>() }
    val httpClient: HttpClient by remember { koin.inject<HttpClient>() }
    val socketClient: SocketClient by remember { koin.inject<SocketClient>() }

    val tickerRepository: TickerRepository = ImplTickerListRepository(database, httpClient)
    val watchlistRepository: WatchListRepository = ImplWatchListRepository(database, socketClient)

    val mainInteractor: HomeScreenInteractor =
        ImplHomeScreenInteractor(tickerRepository, watchlistRepository)

    val scope = rememberCoroutineScope()
    val viewModel = HomeScreenViewModel(scope, mainInteractor)

    val watchListViewModel: WatchlistViewModel by remember { injectScope() }
    val chartViewModel: ChartViewModel by remember { injectScope() }

    Theme {
        HomeScreen(
            serverTimeState = viewModel.serverTime,
            tradingLogState = viewModel.tradingLog,
            tickerListState = viewModel.tickerList,
            watchListUIState = watchListViewModel.uiState,
            chartUIState = chartViewModel.uiState,
            alertTriggerListState = viewModel.alertTriggerList,
            alertEventListState = viewModel.alertEventList,
            alertSettingsUIState = viewModel.alertSettingsUIState,
            onAddToWatchList = watchListViewModel::addToWatchlist,
            onRemoveFromWatchlist = watchListViewModel::removeFromWatchlist,
            onTickerListSearch = viewModel::tickerListSearch,
            onSelectFromWatchlist = chartViewModel::openChart,
            onAlertConditionUpdate = viewModel::updateAlertCondition,
            onAlertConditionSave = viewModel::saveAlertCondition
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
