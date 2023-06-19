package com.mobiledevpro.common.domain.interactor

import com.mobiledepro.main.domain.mapper.toDomain
import com.mobiledepro.main.domain.mapper.toLocal
import com.mobiledepro.main.domain.mapper.toWatchlistLocal
import com.mobiledepro.main.domain.model.Candle
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledevpro.chart.data.repository.ChartRepository
import com.mobiledevpro.tickerlist.data.repository.TickerRepository
import com.mobiledevpro.watchlist.data.repository.WatchListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class ImplMainScreenInteractor(
    private val tickersRepository: TickerRepository,
    private val watchListRepository: WatchListRepository,
    private val chartRepository: ChartRepository
) : MainScreenInteractor {

    private val tickerListSearchTerm = MutableStateFlow("")
    private val syncedSymbolsList = MutableStateFlow(listOf<String>())


    override suspend fun syncTickerList() {
        withContext(Dispatchers.IO) {
            tickersRepository.getTickerListRemote()
                .map { it.toLocal() }
                .also {
                    tickersRepository.cacheTickerListLocal(it)
                }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun syncWatchlist() {
        watchListRepository.getSymbolsListLocal()
            .filter { localSymbolsList ->
                //It allows to avoid re-subscribing to socket every time ticker updates
                localSymbolsList != syncedSymbolsList.value
            }
            .flatMapLatest { symbolList: List<String> ->
                syncedSymbolsList.value = symbolList
                //get updates from the socket
                watchListRepository.subscribeToSymbolListRemote(symbolList)
            }
            .buffer()
            .flowOn(Dispatchers.IO)
            .mapLatest { symbolRemote ->
                //Update watchlist locally
                symbolRemote.toLocal()
                    ?.let { entry -> watchListRepository.updateLocal(entry) }
                    ?: false
            }
            .flowOn(Dispatchers.IO)
            .collect()
    }


    override suspend fun syncChart(ticker: Ticker, timeFrame: String) {
        withContext(Dispatchers.IO) {
            chartRepository.getChartRemote(ticker.symbol, timeFrame)
                .map { it.toLocal(ticker.symbol, timeFrame) }
                .also {
                    println("Candle list to cache: ${it.size}")
                    chartRepository.cacheLocal(it)
                }
        }
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    override fun getServerTime(): Flow<Long> = flow {

        ticker(3000, 0)
            .consumeEach {
                try {
                    tickersRepository.getServerTime().also { timeMs ->
                        emit(timeMs)
                    }
                } catch (e: Exception) {
                    println("Exception: ${e.localizedMessage}")
                    emit(0)
                }

            }

    }.flowOn(Dispatchers.IO)

    override fun getTickerList(): Flow<List<Ticker>> {

        val tickerListFlow = tickersRepository.getTickerListLocal()
            .map { it.toDomain() as List<Ticker> }
        val watchlistFlow = watchListRepository.getListLocal()
            .map { it.toDomain() as List<Ticker> }

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

    override fun getWatchList(): Flow<List<Ticker>> =
        watchListRepository.getListLocal()
            .map { it.toDomain() as List<Ticker> }
            .flowOn(Dispatchers.IO)

    override fun getChart(ticker: Ticker, timeFrame: String): Flow<List<Candle>> =
        chartRepository.getChartLocal(ticker.symbol, timeFrame)
            .map { it.toDomain() as List<Candle> }
            .flowOn(Dispatchers.IO)

    override suspend fun addToWatchList(ticker: Ticker) {
        withContext(Dispatchers.IO) {
            ticker.toWatchlistLocal()
                .also {
                    watchListRepository.addLocal(it)
                }
        }
    }

    override suspend fun removeFromWatchlist(ticker: Ticker) {
        withContext(Dispatchers.IO) {
            ticker.toWatchlistLocal()
                .also { entry ->
                    watchListRepository.removeLocal(entry)
                    watchListRepository.unsubscribeFromRemote(entry).collectLatest {
                        println(":: Thread ${Thread.currentThread().name} :: SOCKET UNSUBSCRIBE :: \n$it")
                    }
                }
        }
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