package com.mobiledevpro.common.domain.interactor

import com.mobiledepro.main.domain.model.Ticker
import kotlinx.coroutines.flow.Flow

interface MainScreenInteractor {
    suspend fun syncTickerList()

    fun getServerTime(): Flow<Long>

    fun getTickerList(): Flow<List<Ticker>>

    fun getWatchList(): Flow<List<Ticker>>

    suspend fun addToWatchList(ticker: Ticker)

    suspend fun removeFromWatchlist(ticker: Ticker)

    suspend fun setTickerListSearch(value: String)

    suspend fun clearTickerListSearch()
}