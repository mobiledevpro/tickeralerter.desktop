package com.mobiledevpro.watchlist.data.repository

import com.mobiledevpro.database.WatchlistEntry
import io.ktor.websocket.*
import kotlinx.coroutines.flow.Flow

interface WatchListRepository {
    fun getListLocal(): Flow<List<WatchlistEntry>>

    fun subscribeToListRemote(list: List<WatchlistEntry>): Flow<Frame.Text>

    suspend fun addLocal(entry: WatchlistEntry)

    suspend fun removeLocal(entry: WatchlistEntry)

    fun unsubscribeFromRemote(ticker: WatchlistEntry): Flow<Frame.Text>
}