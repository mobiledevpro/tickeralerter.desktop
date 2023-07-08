package com.mobiledevpro


import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.mobiledevpro.chart.data.repository.ChartRepository
import com.mobiledevpro.chart.data.repository.ImplChartRepository
import com.mobiledevpro.common.domain.interactor.ImplMainScreenInteractor
import com.mobiledevpro.common.domain.interactor.MainScreenInteractor
import com.mobiledevpro.database.AppDatabase
import com.mobiledevpro.database.DriverFactory
import com.mobiledevpro.feature.main.MainScreen
import com.mobiledevpro.feature.main.MainScreenViewModel
import com.mobiledevpro.network.BinanceHTTPClientFactory
import com.mobiledevpro.network.BinanceSocketClientFactory
import com.mobiledevpro.network.SocketClient
import com.mobiledevpro.tickerlist.data.repository.ImplTickerListRepository
import com.mobiledevpro.tickerlist.data.repository.TickerRepository
import com.mobiledevpro.ui.Theme
import com.mobiledevpro.watchlist.data.repository.ImplWatchListRepository
import com.mobiledevpro.watchlist.data.repository.WatchListRepository
import io.ktor.client.*

@Composable
@Preview
fun App() {

    //database
    // val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    // AppDatabase.Schema.create(driver)

    val isTestnet = false //TODO: add switcher to UI

    val database = AppDatabase(DriverFactory().createDriver())

    val httpClient: HttpClient = BinanceHTTPClientFactory.build(isTestnet)
    val socketClient: SocketClient = BinanceSocketClientFactory.build(isTestnet)

    val tickerRepository: TickerRepository = ImplTickerListRepository(database, httpClient)
    val watchlistRepository: WatchListRepository = ImplWatchListRepository(database, socketClient)
    val chartRepository: ChartRepository = ImplChartRepository(database, httpClient)

    val mainInteractor: MainScreenInteractor =
        ImplMainScreenInteractor(tickerRepository, watchlistRepository, chartRepository)

    val scope = rememberCoroutineScope()
    val viewModel = remember { MainScreenViewModel(scope, mainInteractor) }

    Theme {
        MainScreen(
            serverTimeState = viewModel.serverTime,
            tradingLogState = viewModel.tradingLog,
            tickerListState = viewModel.tickerList,
            watchListState = viewModel.watchlist,
            chartState = viewModel.chart,
            alertTriggerListState = viewModel.alertTriggerList,
            alertEventListState = viewModel.alertEventList,
            alertSettingsUIState = viewModel.alertSettingsUIState,
            onAddToWatchList = viewModel::addToWatchlist,
            onRemoveFromWatchlist = viewModel::removeFromWatchlist,
            onTickerListSearch = viewModel::tickerListSearch,
            onSelectFromWatchlist = viewModel::selectFromWatchlist,
            onAlertConditionUpdate = viewModel::updateAlertCondition,
            onAlertConditionSave = viewModel::saveAlertCondition
        )
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Ticker Alerter",
        state = rememberWindowState(width = 1280.dp, height = 720.dp)
    ) {
        App()
    }
}
