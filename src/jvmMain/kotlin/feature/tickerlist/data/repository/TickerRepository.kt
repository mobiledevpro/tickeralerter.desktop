package feature.tickerlist.data.repository

import common.data.local.model.TickerLocal
import common.data.remote.model.SymbolRemote
import common.domain.model.Ticker
import kotlinx.coroutines.flow.Flow

interface TickerRepository {
    suspend fun getServerTime(): Long

    fun getTickerListLocal(): Flow<List<TickerLocal>>

    suspend fun getTickerListRemote(): List<SymbolRemote>

    suspend fun cacheTickerListLocal(list: List<Ticker>)
}