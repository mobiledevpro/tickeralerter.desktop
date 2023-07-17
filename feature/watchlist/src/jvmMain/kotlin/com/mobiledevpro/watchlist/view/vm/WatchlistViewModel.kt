package com.mobiledevpro.watchlist.view.vm

import com.mobiledepro.main.domain.model.Ticker
import com.mobiledepro.main.util.toLog
import com.mobiledevpro.watchlist.domain.interactor.WatchListInteractor
import com.mobiledevpro.watchlist.view.model.WatchlistUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WatchlistViewModel(
    private val scope: CoroutineScope,
    private val interactor: WatchListInteractor
) {

    private val _uiState: MutableStateFlow<WatchlistUIState> = MutableStateFlow(WatchlistUIState.Empty)
    val uiState: StateFlow<WatchlistUIState> = _uiState.asStateFlow()

    init {
        observeWatchlist()
        syncWatchlist()
    }

    fun addToWatchlist(ticker: Ticker) {
        scope.launch {
            interactor.addToWatchList(ticker)
        }
    }

    fun removeFromWatchlist(ticker: Ticker) {
        scope.launch {
            interactor.removeFromWatchlist(ticker)
        }
    }

    private fun observeWatchlist() {
        //Get watchlist saved locally
        scope.launch {
            interactor.getWatchlist().catch { throwable ->
                println("::ERROR ${throwable.toLog<WatchlistViewModel>()}")
            }.collectLatest { list ->
                println("::${this@WatchlistViewModel.javaClass.name}: ${list.size}")
                _uiState.update {
                    if (list.isEmpty())
                        WatchlistUIState.Empty
                    else
                        WatchlistUIState.Success(list)
                }
            }
        }
    }

    /**
     * Subscribe on getting watchlist update from the socket API
     */
    private fun syncWatchlist() {
        //Update watchlist tickers price from socket
        scope.launch {
            interactor.syncWatchlist()
        }
    }

}