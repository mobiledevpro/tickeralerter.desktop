package com.mobiledevpro.chart.domain.interactor

import com.mobiledepro.main.domain.mapper.toDomain
import com.mobiledepro.main.domain.mapper.toLocal
import com.mobiledepro.main.domain.model.Candle
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledevpro.chart.data.repository.ChartRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ImplChartInteractor(
    private val repository: ChartRepository
) : ChartInteractor {

    private var chartSyncJob: Job? = null

    override fun getChart(ticker: Ticker, timeFrame: String): Flow<List<Candle>> =
        repository.getChartLocal(ticker.symbol, timeFrame)
            .map { it.toDomain<Candle>() }
            .flowOn(Dispatchers.IO)


    override suspend fun syncChart(ticker: Ticker, timeFrame: String) {
        chartSyncJob?.cancel()

        chartSyncJob = CoroutineScope(Dispatchers.IO).launch {
            repository.getChartRemote(ticker.symbol, timeFrame)
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .flowOn(Dispatchers.IO)
                .map { candleListRemote ->
                    candleListRemote.toLocal(ticker.symbol, timeFrame)
                        .let {
                            println("Candle list to cache: ${it.size}")
                            repository.cacheLocal(it)
                        }

                }
                .collect()
        }

    }

}