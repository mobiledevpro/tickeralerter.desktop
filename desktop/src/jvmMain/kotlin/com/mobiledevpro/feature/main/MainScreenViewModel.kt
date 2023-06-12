package com.mobiledevpro.feature.main

import com.mobiledepro.main.domain.model.Ticker
import com.mobiledevpro.common.domain.interactor.MainScreenInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class MainScreenViewModel(
    private val scope: CoroutineScope,
    private val interactor: MainScreenInteractor
) {

    private val _tradingLog = MutableStateFlow<List<String>>(emptyList())
    val tradingLog: StateFlow<List<String>> = _tradingLog.asStateFlow()

    private val _tickerList = MutableStateFlow<List<Ticker>>(emptyList())
    val tickerList: StateFlow<List<Ticker>> = _tickerList.asStateFlow()

    private val _watchlist = MutableStateFlow<List<Ticker>>(emptyList())
    val watchlist: StateFlow<List<Ticker>> = _watchlist.asStateFlow()

    private val _serverTime = MutableStateFlow(0L)
    val serverTime: StateFlow<Long> = _serverTime


    init {
        observeNetworkConnection()
       // observeLog()
        observeWatchlist()
        observeTickerList()
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

    fun tickerListSearch(value: String) {
        scope.launch {
            if (value.isEmpty())
                interactor.clearTickerListSearch()
            else
                interactor.setTickerListSearch(value)
        }
    }

    private fun observeLog() {
        scope.launch(Dispatchers.IO) {

            val mutableList = ArrayList<String>()

            for (i in 0..9) {
                val event: String = "${getTime()} | Test event $i"
                mutableList.add(event)
                println("Add to log ${mutableList.size}: Thread ${Thread.currentThread().name}")

                _tradingLog.update { ArrayList(mutableList.reversed()) }

                Thread.sleep(500)

                println("list size ${mutableList.size}")
            }

        }


    }

    private fun observeTickerList() {
        scope.launch {
            interactor.syncTickerList()
        }

        scope.launch {
            interactor.getTickerList().collectLatest { list ->
                println("Get local ticker list: ${list.size}")
                _tickerList.update { list }
            }
        }
    }

    private fun observeWatchlist() {

        //Get watchlist saved locally
        scope.launch {
            interactor.getWatchList().collectLatest { list ->
                println("Get local watchlist: ${list.size}")
                _watchlist.update { list }
            }
        }

        //Update watchlist tickers price from socket
        scope.launch {
            interactor.syncWatchlist()
        }
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    private fun observeNetworkConnection() {
        scope.launch {
            interactor.getServerTime().collectLatest { timeMs ->
                println("Time $timeMs")
                _serverTime.update { timeMs }
            }
        }
    }

    private fun getTime(): Long = Calendar.getInstance(Locale.getDefault()).timeInMillis
}