package com.mobiledevpro.tickerlist.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.mobiledevpro.common.data.remote.model.ExchangeInfoRemote
import com.mobiledevpro.database.AppDatabase
import com.mobiledevpro.database.TickerEntry
import com.mobiledevpro.network.api.getExchangeInfo
import com.mobiledevpro.network.model.TickerRemote
import io.ktor.client.*
import io.ktor.client.call.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlin.system.measureTimeMillis

class ImplTickerListRepository(
    private val database: AppDatabase,
    private val httpClient: HttpClient
) : TickerListRepository {

    override fun getTickerListLocal(): Flow<List<TickerEntry>> =
        database.tickerListQueries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)

    override suspend fun getTickerListRemote(): List<TickerRemote> =
        httpClient.getExchangeInfo().body<ExchangeInfoRemote>().symbols

    override suspend fun cacheTickerListLocal(list: List<TickerEntry>) {
        measureTimeMillis {
            database.tickerListQueries.transaction {
                list.forEach { ticker ->
                    database.tickerListQueries.insertItem(ticker)
                }
            }
        }.also { transactionTime ->
            println("cache ticker list time: $transactionTime ms")
        }
    }
}