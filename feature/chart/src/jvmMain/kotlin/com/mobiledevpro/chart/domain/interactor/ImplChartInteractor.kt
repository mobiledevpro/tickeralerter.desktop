package com.mobiledevpro.chart.domain.interactor

import com.mobiledepro.main.domain.mapper.toDomain
import com.mobiledepro.main.domain.mapper.toLocal
import com.mobiledepro.main.domain.model.Candle
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledevpro.chart.data.repository.ChartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ImplChartInteractor(
    private val repository: ChartRepository
) : ChartInteractor {

    override fun getChart(ticker: Ticker, timeFrame: String): Flow<List<Candle>> =
        repository.getChartLocal(ticker.symbol, timeFrame)
            .map { it.toDomain() as List<Candle> }
            .flowOn(Dispatchers.IO)


    override suspend fun syncChart(ticker: Ticker, timeFrame: String) {
        withContext(Dispatchers.IO) {
            repository.getChartRemote(ticker.symbol, timeFrame)
                .map { it.toLocal(ticker.symbol, timeFrame) }
                .also {
                    println("Candle list to cache: ${it.size}")
                    repository.cacheLocal(it)
                }
        }
    }

}