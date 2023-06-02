package com.mobiledevpro.feature.tickerlist.data.repository

import com.mobiledevpro.common.data.local.model.TickerLocal
import com.mobiledevpro.common.data.remote.model.SymbolRemote
import com.mobiledevpro.common.domain.model.Ticker
import kotlinx.coroutines.flow.Flow

interface TickerRepository {
    suspend fun getServerTime(): Long

    fun getTickerListLocal(): Flow<List<TickerLocal>>

    suspend fun getTickerListRemote(): List<SymbolRemote>

    suspend fun cacheTickerListLocal(list: List<Ticker>)
}