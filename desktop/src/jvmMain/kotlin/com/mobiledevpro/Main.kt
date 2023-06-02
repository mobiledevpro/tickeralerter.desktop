package com.mobiledevpro

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.mobiledevpro.common.domain.interactor.ImplMainScreenInteractor
import com.mobiledevpro.common.domain.interactor.MainScreenInteractor
import com.mobiledevpro.feature.main.MainScreen
import com.mobiledevpro.feature.main.MainScreenViewModel
import com.mobiledevpro.feature.tickerlist.data.repository.ImplTickerListRepository
import com.mobiledevpro.feature.tickerlist.data.repository.TickerRepository
import com.mobiledevpro.network.BinanceHTTPClientFactory
import com.mobiledevpro.ui.Theme
import io.ktor.client.*

@Composable
@Preview
fun App() {

    //database
    // val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    // AppDatabase.Schema.create(driver)


    //TickerTa

    val httpClient: HttpClient = BinanceHTTPClientFactory.build()
    val tickerRepository: TickerRepository = ImplTickerListRepository(httpClient)
    val mainInteractor: MainScreenInteractor = ImplMainScreenInteractor(tickerRepository)

    val scope = rememberCoroutineScope()
    val viewModel = remember { MainScreenViewModel(scope, mainInteractor) }

    Theme {
        MainScreen(
            serverTimeState = viewModel.serverTime,
            tradingLogState = viewModel.tradingLog,
            tickerListState = viewModel.tickerList
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
