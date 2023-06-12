package com.mobiledevpro.watchlist.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.mobiledepro.main.domain.mapper.toSocketRequest
import com.mobiledevpro.database.AppDatabase
import com.mobiledevpro.database.WatchlistEntry
import com.mobiledevpro.network.api.BinanceSocket
import com.mobiledevpro.network.wsSubscribe
import com.mobiledevpro.network.wsUnsubscribe
import io.ktor.client.*
import io.ktor.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class ImplWatchListRepository(
    private val database: AppDatabase,
    private val socketClient: HttpClient
) : WatchListRepository {

    override fun getListLocal(): Flow<List<WatchlistEntry>> =
        database.watchlistQueries.selectAll()
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

    override fun subscribeToListRemote(list: List<WatchlistEntry>): Flow<Frame.Text> =
        list.toSocketRequest(BinanceSocket.Method.SUBSCRIBE, "miniTicker")
            .let(socketClient::wsSubscribe)

    override fun unsubscribeFromRemote(ticker: WatchlistEntry): Flow<Frame.Text> =
        ticker.toSocketRequest(BinanceSocket.Method.UNSUBSCRIBE, "miniTicker")
            .let(socketClient::wsUnsubscribe)

}