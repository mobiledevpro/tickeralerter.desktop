package com.mobiledevpro.tickerlist.domain.interactor

import com.mobiledepro.main.domain.model.Ticker
import kotlinx.coroutines.flow.Flow

interface TickerListInteractor {
    fun getTickerList(): Flow<List<Ticker>>

    suspend fun syncTickerList()

    suspend fun setTickerListSearch(value: String)

    suspend fun clearTickerListSearch()
}