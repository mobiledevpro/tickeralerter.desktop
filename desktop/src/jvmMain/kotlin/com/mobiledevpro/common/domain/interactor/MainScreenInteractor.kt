package com.mobiledevpro.common.domain.interactor

import com.mobiledepro.main.domain.model.Ticker
import kotlinx.coroutines.flow.Flow

interface MainScreenInteractor {
    suspend fun syncTickerList()

    fun getServerTime(): Flow<Long>
    fun getTickerList(): Flow<List<Ticker>>
}