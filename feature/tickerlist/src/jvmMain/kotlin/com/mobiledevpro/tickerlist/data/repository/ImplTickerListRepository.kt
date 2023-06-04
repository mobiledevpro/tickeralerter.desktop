package com.mobiledevpro.tickerlist.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.mobiledevpro.common.data.remote.model.ExchangeInfoRemote
import com.mobiledevpro.common.data.remote.model.ServerTimeRemote
import com.mobiledevpro.common.data.remote.model.SymbolRemote
import com.mobiledevpro.database.AppDatabase
import com.mobiledevpro.database.TickerEntry
import com.mobiledevpro.feature.tickerlist.data.repository.TickerRepository
import com.mobiledevpro.network.getExchangeInfo
import com.mobiledevpro.network.getServerTime
import io.ktor.client.*
import io.ktor.client.call.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlin.system.measureTimeMillis

class ImplTickerListRepository(
    private val httpClient: HttpClient,
    private val database: AppDatabase
) : TickerRepository {

    override suspend fun getServerTime(): Long = httpClient.getServerTime().let { resp ->
        val body: ServerTimeRemote = resp.body()
        body.serverTime
    }

    override fun getTickerListLocal(): Flow<List<TickerEntry>> =
        database.appDatabaseQueries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)

    override suspend fun getTickerListRemote(): List<SymbolRemote> =
        httpClient.getExchangeInfo().body<ExchangeInfoRemote>().symbols

    override suspend fun cacheTickerListLocal(list: List<TickerEntry>) {
        measureTimeMillis {
            database.appDatabaseQueries.transaction {
                list.forEach { ticker ->
                    database.appDatabaseQueries.insertItem(
                        symbol = ticker.symbol,
                        lastPrice = ticker.lastPrice,
                        priceChange = ticker.priceChange,
                        priceChangePercent = ticker.priceChangePercent
                    )
                }
            }
        }.also { transactionTime ->
            println("cache ticker list time: $transactionTime ms")
        }
    }
}