package com.mobiledevpro.watchlist.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.mobiledepro.main.domain.mapper.toSocketRequest
import com.mobiledepro.main.domain.mapper.toWatchlistSymbol
import com.mobiledevpro.database.AppDatabase
import com.mobiledevpro.database.WatchlistEntry
import com.mobiledevpro.network.SocketClient
import com.mobiledevpro.network.api.BinanceSocket
import com.mobiledevpro.network.model.WatchlistSymbolRemote
import com.mobiledevpro.network.wsSubscribe
import com.mobiledevpro.network.wsUnsubscribe
import io.ktor.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ImplWatchListRepository(
    private val database: AppDatabase,
    private val socketClient: SocketClient
) : WatchListRepository {

    override fun getListLocal(): Flow<List<WatchlistEntry>> =
        database.watchlistQueries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)

    override fun getSymbolsListLocal(): Flow<List<String>> =
        database.watchlistQueries.selectSymbols()
            .asFlow()
            .mapToList(Dispatchers.IO)


    override suspend fun addLocal(entry: WatchlistEntry) {
        //check entry is exists
        val isExist = database.watchlistQueries.selectIsExist(entry.symbol)
            .executeAsOne() > 0

        if (!isExist)
            database.watchlistQueries.insertItem(
                symbol = entry.symbol,
                lastPrice = entry.lastPrice,
                priceChange = entry.priceChange,
                priceChangePercent = entry.priceChangePercent
            )
    }

    override suspend fun removeLocal(entry: WatchlistEntry) {
        database.watchlistQueries.deleteItem(entry.symbol)
    }

    override suspend fun updateLocal(entry: WatchlistEntry): Boolean {
        println("Update local: ${Thread.currentThread().name}")
        database.watchlistQueries.insertItem(
            symbol = entry.symbol,
            lastPrice = entry.lastPrice,
            priceChange = entry.priceChange,
            priceChangePercent = entry.priceChangePercent
        )

        return true
    }

    override fun subscribeToSymbolListRemote(list: List<String>): Flow<WatchlistSymbolRemote> =
        list.toSocketRequest(BinanceSocket.Method.SUBSCRIBE, "ticker")
            .let(socketClient::wsSubscribe)
            .map {
                println(":: Thread ${Thread.currentThread().name} :: SOCKET :: \n${it.readText()}")
                it.toWatchlistSymbol()
            }

    override fun unsubscribeFromRemote(ticker: WatchlistEntry): Flow<String> =
        ticker.toSocketRequest(BinanceSocket.Method.UNSUBSCRIBE, "ticker")
            .let(socketClient::wsUnsubscribe)
            .map(Frame.Text::readText)

}