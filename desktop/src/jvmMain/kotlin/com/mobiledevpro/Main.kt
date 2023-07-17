package com.mobiledevpro


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.mobiledevpro.chart.data.repository.ChartRepository
import com.mobiledevpro.chart.data.repository.ImplChartRepository
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
import com.mobiledevpro.watchlist.di.scopeWatchlist
import com.mobiledevpro.watchlist.view.vm.WatchlistViewModel
import di.appModules
import io.ktor.client.*
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

@Composable
fun App() {

    val isTestnet = false //TODO: add switcher to UI
    val koin: Koin = getKoin()

    val database by getKoin().inject<AppDatabase>()
    val httpClient: HttpClient by getKoin().inject<HttpClient>()
    val socketClient: SocketClient by getKoin().inject<SocketClient>()

    val tickerRepository: TickerRepository = ImplTickerListRepository(database, httpClient)
    val watchlistRepository: WatchListRepository = ImplWatchListRepository(database, socketClient)
    val chartRepository: ChartRepository = ImplChartRepository(database, httpClient)

    val mainInteractor: HomeScreenInteractor =
        ImplHomeScreenInteractor(tickerRepository, watchlistRepository, chartRepository)

    val scope = rememberCoroutineScope()
    val viewModel = HomeScreenViewModel(scope, mainInteractor)

    val watchListViewModel: WatchlistViewModel by remember { scopeWatchlist().inject<WatchlistViewModel>() }

    Theme {
        HomeScreen(
            serverTimeState = viewModel.serverTime,
            tradingLogState = viewModel.tradingLog,
            tickerListState = viewModel.tickerList,
            watchListUIState = watchListViewModel.uiState,
            chartState = viewModel.chart,
            alertTriggerListState = viewModel.alertTriggerList,
            alertEventListState = viewModel.alertEventList,
            alertSettingsUIState = viewModel.alertSettingsUIState,
            onAddToWatchList = watchListViewModel::addToWatchlist,
            onRemoveFromWatchlist = watchListViewModel::removeFromWatchlist,
            onTickerListSearch = viewModel::tickerListSearch,
            onSelectFromWatchlist = viewModel::selectFromWatchlist,
            onAlertConditionUpdate = viewModel::updateAlertCondition,
            onAlertConditionSave = viewModel::saveAlertCondition
        )
    }
}

fun main() = application {

    startKoin {
        modules(
            appModules
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
