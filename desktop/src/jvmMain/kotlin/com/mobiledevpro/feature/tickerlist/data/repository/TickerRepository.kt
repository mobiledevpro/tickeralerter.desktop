package com.mobiledevpro.feature.tickerlist.data.repository

import com.mobiledevpro.common.data.remote.model.SymbolRemote
import com.mobiledevpro.database.TickerEntry
import kotlinx.coroutines.flow.Flow

interface TickerRepository {
    suspend fun getServerTime(): Long

    fun getTickerListLocal(): Flow<List<TickerEntry>>

    suspend fun getTickerListRemote(): List<SymbolRemote>

    suspend fun cacheTickerListLocal(list: List<TickerEntry>)
}