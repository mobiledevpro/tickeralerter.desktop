package com.mobiledevpro.chart.view.vm

import com.mobiledepro.main.domain.model.Chart
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledepro.main.view.BaseViewModel
import com.mobiledevpro.chart.domain.interactor.ChartInteractor
import com.mobiledevpro.chart.view.state.ChartUIState
import io.ktor.utils.io.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChartViewModel(
    private val coroutineScope: CoroutineScope,
    private val interactor: ChartInteractor
) : BaseViewModel<ChartUIState>() {

    override fun initUIState(): ChartUIState = ChartUIState.Empty

    fun openChart(ticker: Ticker) {
        observeChartCandleList(ticker)
    }

    private fun observeChartCandleList(ticker: Ticker) {
        coroutineScope.launch {
            interactor.getChart(ticker, "1h").collectLatest { candleList ->
                println("Get local candle list: ${candleList.size}")
                _uiState.update {
                    ChartUIState.Success(chartData = Chart(candleList))
                }
            }
        }

        coroutineScope.launch {
            try {
                interactor.syncChart(ticker, "1h")
            } catch (e: Exception) {
                println("observeChartCandleList: ERROR ${e.printStack()}")
            }
        }
    }
}