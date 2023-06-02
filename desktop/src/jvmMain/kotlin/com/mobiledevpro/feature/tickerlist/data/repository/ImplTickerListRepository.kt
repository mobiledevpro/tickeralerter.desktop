package com.mobiledevpro.feature.tickerlist.data.repository

import com.mobiledevpro.common.data.local.model.TickerLocal
import com.mobiledevpro.common.data.remote.model.ExchangeInfoRemote
import com.mobiledevpro.common.data.remote.model.ServerTimeRemote
import com.mobiledevpro.common.data.remote.model.SymbolRemote
import com.mobiledevpro.common.domain.model.Ticker
import com.mobiledevpro.network.getExchangeInfo
import com.mobiledevpro.network.getServerTime
import io.ktor.client.*
import io.ktor.client.call.*
import kotlinx.coroutines.flow.Flow

class ImplTickerListRepository(
    private val httpClient: HttpClient
) : TickerRepository {

    override suspend fun getServerTime(): Long = httpClient.getServerTime().let { resp ->
        val body: ServerTimeRemote = resp.body()
        body.serverTime
    }

    override fun getTickerListLocal(): Flow<List<TickerLocal>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTickerListRemote(): List<SymbolRemote> =
        httpClient.getExchangeInfo().body<ExchangeInfoRemote>().symbols

    override suspend fun cacheTickerListLocal(list: List<Ticker>) {
        TODO("Not yet implemented")
    }
}