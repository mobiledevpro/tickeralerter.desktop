package com.mobiledevpro.chart.domain.interactor

import com.mobiledepro.main.domain.model.Candle
import com.mobiledepro.main.domain.model.Ticker
import kotlinx.coroutines.flow.Flow

interface ChartInteractor {

    fun getChart(ticker: Ticker, timeFrame: String): Flow<List<Candle>>

    suspend fun syncChart(ticker: Ticker, timeFrame: String)
}