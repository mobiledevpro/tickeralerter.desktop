package com.mobiledevpro.watchlist.data.repository

import com.mobiledevpro.database.WatchlistEntry
import com.mobiledevpro.network.model.WatchlistSymbolRemote
import kotlinx.coroutines.flow.Flow

interface WatchListRepository {
    fun getListLocal(): Flow<List<WatchlistEntry>>

    fun getSymbolsListLocal(): Flow<List<String>>

    fun subscribeToSymbolListRemote(list: List<String>): Flow<WatchlistSymbolRemote>

    suspend fun addLocal(entry: WatchlistEntry)

    suspend fun removeLocal(entry: WatchlistEntry)

    suspend fun updateLocal(entry: WatchlistEntry): Boolean

    fun unsubscribeFromRemote(ticker: WatchlistEntry): Flow<String>
}