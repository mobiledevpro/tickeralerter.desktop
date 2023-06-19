package com.mobiledevpro.chart.data.repository

import com.mobiledevpro.database.CandleEntry
import com.mobiledevpro.network.model.CandleRemote
import kotlinx.coroutines.flow.Flow

interface ChartRepository {
    fun getChartLocal(symbol: String, timeFrame: String): Flow<List<CandleEntry>>

    suspend fun getChartRemote(symbol: String, timeFrame: String): List<CandleRemote>

    suspend fun cacheLocal(entryList: List<CandleEntry>)
}