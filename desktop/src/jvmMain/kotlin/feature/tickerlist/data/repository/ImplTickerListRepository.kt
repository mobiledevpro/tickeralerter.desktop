package feature.tickerlist.data.repository

import common.data.local.model.TickerLocal
import common.data.remote.model.ExchangeInfoRemote
import common.data.remote.model.ServerTimeRemote
import common.data.remote.model.SymbolRemote
import common.domain.model.Ticker
import io.ktor.client.*
import io.ktor.client.call.*
import kotlinx.coroutines.flow.Flow
import network.getExchangeInfo
import network.getServerTime

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