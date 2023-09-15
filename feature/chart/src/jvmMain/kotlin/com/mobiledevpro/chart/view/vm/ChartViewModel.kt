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

    init {
        //open chart by default for BTC
        openDefaultChart()
    }

    fun openChart(ticker: Ticker) {
        _uiState.update { ChartUIState.Loading }
        observeChartCandleList(ticker)
    }

    private fun observeChartCandleList(ticker: Ticker) {
        coroutineScope.launch {
            interactor.getChart(ticker, "15m").collectLatest { candleList ->
                println("Get local candle list: ${candleList.size}")

                if (candleList.isNotEmpty())
                    _uiState.update {
                        ChartUIState.Success(chartData = Chart(candleList))
                    }
            }
        }

        coroutineScope.launch {
            try {
                interactor.syncChart(ticker, "15m")
            } catch (e: Exception) {
                println("observeChartCandleList: ERROR ${e.printStack()}")
            }
        }
    }

    private fun openDefaultChart() {
        Ticker(
            symbol = "BTCUSDT",
            baseAsset = "BTC",
            contractType = "PERPETUAL",
            lastPrice = 0.0,
            priceChange = 0.0,
            priceChangePercent = 0.0
        ).also(this::openChart)
    }
}