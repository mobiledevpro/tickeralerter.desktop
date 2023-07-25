package com.mobiledevpro.tickerlist.data.repository

import com.mobiledevpro.database.TickerEntry
import com.mobiledevpro.network.model.TickerRemote
import kotlinx.coroutines.flow.Flow

interface TickerListRepository {

    fun getTickerListLocal(): Flow<List<TickerEntry>>

    suspend fun getTickerListRemote(): List<TickerRemote>

    suspend fun cacheTickerListLocal(list: List<TickerEntry>)
}