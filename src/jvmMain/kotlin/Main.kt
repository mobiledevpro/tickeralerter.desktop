import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import feature.main.MainScreen
import feature.main.MainScreenViewModel
import ui.Theme

@Composable
@Preview
fun App() {

    val scope = rememberCoroutineScope()
    val viewModel = remember { MainScreenViewModel(scope) }

    Theme {
        MainScreen(
            viewModel.onlineStatus,
            viewModel.tradingLog
        )
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Ticker Alerter") {
        App()
    }
}
