package com.mobiledevpro.watchlist.view.vm

import com.mobiledepro.main.domain.model.Ticker
import com.mobiledepro.main.util.toLog
import com.mobiledepro.main.view.BaseViewModel
import com.mobiledevpro.watchlist.domain.interactor.WatchListInteractor
import com.mobiledevpro.watchlist.view.state.WatchlistUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WatchlistViewModel(
    private val coroutineScope: CoroutineScope,
    private val interactor: WatchListInteractor
) : BaseViewModel<WatchlistUIState>() {

    override fun initUIState(): WatchlistUIState = WatchlistUIState.Empty

    init {
        observeWatchlist()
        // syncWatchlist()

        addDefaultSymbols()
    }

    fun addToWatchlist(ticker: Ticker) {
        coroutineScope.launch {
            interactor.addToWatchList(ticker)
        }
    }

    fun removeFromWatchlist(ticker: Ticker) {
        coroutineScope.launch {
            interactor.removeFromWatchlist(ticker)
        }
    }

    private fun observeWatchlist() {
        //Get watchlist saved locally
        coroutineScope.launch {
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
        coroutineScope.launch {
            interactor.syncWatchlist()
        }
    }

    private fun addDefaultSymbols() {
        Ticker(
            symbol = "BTCUSDT",
            baseAsset = "BTC",
            contractType = "PERPETUAL",
            lastPrice = 0.0,
            priceChange = 0.0,
            priceChangePercent = 0.0
        ).also(this::addToWatchlist)

        Ticker(
            symbol = "ETHUSDT",
            baseAsset = "ETH",
            contractType = "PERPETUAL",
            lastPrice = 0.0,
            priceChange = 0.0,
            priceChangePercent = 0.0
        ).also(this::addToWatchlist)
    }

}