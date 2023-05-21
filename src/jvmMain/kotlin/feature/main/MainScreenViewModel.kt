package feature.main

import common.domain.interactor.MainScreenInteractor
import common.domain.model.Ticker
import common.domain.model.fakeTickerListFirst
import common.domain.model.fakeTickerListSecond
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.ticker
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

    private val _serverTime = MutableStateFlow(0L)
    val serverTime: StateFlow<Long> = _serverTime

    init {
        observeNetworkConnection()
        // observeLog()
        //observeTickerList()
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

    @OptIn(ObsoleteCoroutinesApi::class)
    private fun observeTickerList() {
        scope.launch(Dispatchers.IO) {
            //TODO: for demoi
            ticker(2000, 0).consumeEach {
                fakeTickerListFirst()
                    .also { list ->
                        _tickerList.update { list }
                    }
            }
        }

        scope.launch(Dispatchers.IO) {
            ticker(2000, 1000).consumeEach {
                fakeTickerListSecond()
                    .also { list ->
                        _tickerList.update { list }
                    }
            }
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