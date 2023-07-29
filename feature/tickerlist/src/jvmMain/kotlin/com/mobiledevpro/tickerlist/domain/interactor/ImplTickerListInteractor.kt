package com.mobiledevpro.tickerlist.domain.interactor

import com.mobiledepro.main.domain.mapper.toDomain
import com.mobiledepro.main.domain.mapper.toLocal
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledevpro.tickerlist.data.repository.TickerListRepository
import com.mobiledevpro.watchlist.data.repository.WatchListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class ImplTickerListInteractor(
    private val tickersRepository: TickerListRepository,
    private val watchListRepository: WatchListRepository
) : TickerListInteractor {

    private val tickerListSearchTerm = MutableStateFlow("")

    override suspend fun syncTickerList() {
        withContext(Dispatchers.IO) {
            tickersRepository.getTickerListRemote()
                .map { it.toLocal() }
                .also {
                    tickersRepository.cacheTickerListLocal(it)
                }
        }
    }

    override fun getTickerList(): Flow<List<Ticker>> {

        val tickerListFlow = tickersRepository.getTickerListLocal()
            .map { it.toDomain<Ticker>() }
        val watchlistFlow = watchListRepository.getListLocal()
            .map { it.toDomain<Ticker>() }

        return combine(
            tickerListFlow,
            watchlistFlow,
            tickerListSearchTerm
        ) { tickers: List<Ticker>, watchlist: List<Ticker>, searchTerm: String ->
            tickers.onEach { ticker ->
                ticker.selected = watchlist.find { it.symbol == ticker.symbol } != null
            }.let { list ->
                if (searchTerm.isNotEmpty())
                    list.filter { it.symbol.lowercase().contains(searchTerm.lowercase()) }
                else
                    list
            }

        }.flowOn(Dispatchers.IO)
    }

    override suspend fun setTickerListSearch(value: String) {
        withContext(Dispatchers.IO) {
            tickerListSearchTerm.value = value
        }
    }

    override suspend fun clearTickerListSearch() {
        withContext(Dispatchers.IO) {
            tickerListSearchTerm.value = ""
        }
    }

}