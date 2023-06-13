package com.mobiledevpro.tickerlist.data.repository

import com.mobiledevpro.common.data.remote.model.TickerRemote
import com.mobiledevpro.database.TickerEntry
import kotlinx.coroutines.flow.Flow

interface TickerRepository {
    suspend fun getServerTime(): Long

    fun getTickerListLocal(): Flow<List<TickerEntry>>

    suspend fun getTickerListRemote(): List<TickerRemote>

    suspend fun cacheTickerListLocal(list: List<TickerEntry>)
}