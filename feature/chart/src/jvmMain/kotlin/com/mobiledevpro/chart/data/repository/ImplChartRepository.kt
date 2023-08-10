package com.mobiledevpro.chart.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.mobiledepro.main.domain.mapper.toCandleList
import com.mobiledevpro.database.AppDatabase
import com.mobiledevpro.database.CandleEntry
import com.mobiledevpro.network.getChart
import com.mobiledevpro.network.model.CandleRemote
import io.ktor.client.*
import io.ktor.client.call.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.decodeFromString
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

    override suspend fun getChartRemote(symbol: String, timeFrame: String): List<CandleRemote> =
        httpClient.getChart(symbol, timeFrame)
            .body<String>().let { bodyString ->
                Json.decodeFromString<JsonArray>(bodyString)
                    .let(JsonArray::toCandleList)
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

}