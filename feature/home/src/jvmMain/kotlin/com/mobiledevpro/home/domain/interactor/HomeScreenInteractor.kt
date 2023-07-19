package com.mobiledevpro.home.domain.interactor

import com.mobiledepro.main.domain.model.Ticker
import kotlinx.coroutines.flow.Flow

interface HomeScreenInteractor {
    suspend fun syncTickerList()

    fun getServerTime(): Flow<Long>

    fun getTickerList(): Flow<List<Ticker>>

    suspend fun setTickerListSearch(value: String)

    suspend fun clearTickerListSearch()
}