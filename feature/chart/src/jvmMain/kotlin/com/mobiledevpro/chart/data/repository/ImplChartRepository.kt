package com.mobiledevpro.chart.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.mobiledepro.main.domain.mapper.toCandleList
import com.mobiledevpro.database.AppDatabase
import com.mobiledevpro.database.CandleEntry
import com.mobiledevpro.network.api.getChart
import com.mobiledevpro.network.model.CandleRemote
import io.ktor.client.*
import io.ktor.client.call.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlin.system.measureTimeMillis

class ImplChartRepository(
    private val database: AppDatabase,
    private val httpClient: HttpClient
) : ChartRepository {

    override fun getChartLocal(symbol: String, timeFrame: String): Flow<List<CandleEntry>> =
        database.candleListQueries.selectBySymbol(symbol, timeFrame)
            .asFlow()
            .mapToList(Dispatchers.IO)

    @OptIn(ObsoleteCoroutinesApi::class)
    override fun getChartRemote(symbol: String, timeFrame: String): Flow<List<CandleRemote>> = flow {
        ticker(delayMillis = SYNC_CHART_INTERVAL_MS, initialDelayMillis = 0)
            .consumeEach {
                httpClient.getChart(symbol, timeFrame)
                    .body<String>().let { bodyString ->
                        Json.decodeFromString<JsonArray>(bodyString)
                            .let(JsonArray::toCandleList)
                    }.let {
                        emit(it)
                    }
            }
    }

    override suspend fun cacheLocal(entryList: List<CandleEntry>) {
        measureTimeMillis {
            database.candleListQueries.transaction {
                entryList.forEach { candle ->

                    val exist = database.candleListQueries
                        .checkIsExist(candle.symbol, candle.timeFrame, candle.openTime, candle.closeTime)
                        .executeAsOne()

                    println("Candle ${candle.openTime} | high ${candle.priceHigh} | low ${candle.priceLow} | open ${candle.priceOpen} | close ${candle.priceClose} exist $exist")

                    if (exist == 0L)
                        database.candleListQueries.insertItem(candle)
                }
            }
        }.also { transactionTime ->
            println("Cache candle list time: $transactionTime ms")
        }
    }

    companion object {
        const val SYNC_CHART_INTERVAL_MS: Long = 30_000 //30 sec
    }

}