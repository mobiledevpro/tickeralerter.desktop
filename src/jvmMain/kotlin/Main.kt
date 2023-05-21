import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import common.domain.interactor.ImplMainScreenInteractor
import common.domain.interactor.MainScreenInteractor
import feature.main.MainScreen
import feature.main.MainScreenViewModel
import feature.tickerlist.data.repository.ImplTickerListRepository
import feature.tickerlist.data.repository.TickerRepository
import io.ktor.client.*
import network.BinanceHTTPClientFactory
import ui.Theme

@Composable
@Preview
fun App() {

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
