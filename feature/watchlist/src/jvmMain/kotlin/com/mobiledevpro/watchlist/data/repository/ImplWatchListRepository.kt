package com.mobiledevpro.watchlist.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.mobiledevpro.database.AppDatabase
import com.mobiledevpro.database.WatchlistEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class ImplWatchListRepository(
    private val database: AppDatabase
) : WatchListRepository {
    override fun getListLocal(): Flow<List<WatchlistEntry>> =
        database.watchlistQueries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)

    override suspend fun addLocal(entry: WatchlistEntry) {
        database.watchlistQueries.insertItem(
            symbol = entry.symbol,
            lastPrice = entry.lastPrice,
            priceChange = entry.priceChange,
            priceChangePercent = entry.priceChangePercent
        )
    }
}