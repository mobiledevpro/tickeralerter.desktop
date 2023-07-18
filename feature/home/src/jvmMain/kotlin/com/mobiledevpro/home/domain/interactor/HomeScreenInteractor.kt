package com.mobiledevpro.home.domain.interactor

import com.mobiledepro.main.domain.model.Ticker
import kotlinx.coroutines.flow.Flow

interface HomeScreenInteractor {
    suspend fun syncTickerList()

    suspend fun syncWatchlist()

    fun getServerTime(): Flow<Long>

    fun getTickerList(): Flow<List<Ticker>>

    fun getWatchList(): Flow<List<Ticker>>

    suspend fun setTickerListSearch(value: String)

    suspend fun clearTickerListSearch()
}