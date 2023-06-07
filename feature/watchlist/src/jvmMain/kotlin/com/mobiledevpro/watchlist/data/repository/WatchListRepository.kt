package com.mobiledevpro.watchlist.data.repository

import com.mobiledevpro.database.WatchlistEntry
import kotlinx.coroutines.flow.Flow

interface WatchListRepository {
    fun getListLocal(): Flow<List<WatchlistEntry>>

    suspend fun addLocal(entry: WatchlistEntry)
}