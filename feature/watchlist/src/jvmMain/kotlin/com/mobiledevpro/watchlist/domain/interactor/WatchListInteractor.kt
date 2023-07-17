package com.mobiledevpro.watchlist.domain.interactor

import com.mobiledepro.main.domain.model.Ticker
import kotlinx.coroutines.flow.Flow

interface WatchListInteractor {
    fun getWatchlist(): Flow<List<Ticker>>

    suspend fun syncWatchlist()

    suspend fun addToWatchList(ticker: Ticker)

    suspend fun removeFromWatchlist(ticker: Ticker)
}