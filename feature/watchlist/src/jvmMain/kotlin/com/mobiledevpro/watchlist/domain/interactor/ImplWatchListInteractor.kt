package com.mobiledevpro.watchlist.domain.interactor

import com.mobiledepro.main.domain.mapper.toDomain
import com.mobiledepro.main.domain.mapper.toLocal
import com.mobiledepro.main.domain.mapper.toWatchlistLocal
import com.mobiledepro.main.domain.model.Ticker
import com.mobiledevpro.watchlist.data.repository.WatchListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class ImplWatchListInteractor(
    private val watchListRepository: WatchListRepository
) : WatchListInteractor {

    private val syncedSymbolsList = MutableStateFlow(listOf<String>())

    override fun getWatchlist(): Flow<List<Ticker>> =
        watchListRepository.getListLocal()
            .map { it.toDomain<Ticker>() }
            .flowOn(Dispatchers.IO)

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

    override suspend fun addToWatchList(ticker: Ticker) {
        withContext(Dispatchers.IO) {
            ticker.toWatchlistLocal()
                .also {
                    println("::Thread ${Thread.currentThread().name} :: addToWatchList :: \n$it")
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
                        println("::Thread ${Thread.currentThread().name} :: SOCKET UNSUBSCRIBE :: \n$it")
                    }
                }
        }
    }

}