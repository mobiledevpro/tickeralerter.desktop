package com.mobiledevpro.home.view.vm

import com.mobiledepro.main.domain.model.*
import com.mobiledepro.main.view.BaseViewModel
import com.mobiledevpro.home.domain.interactor.HomeScreenInteractor
import com.mobiledevpro.home.view.state.HomeUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class HomeScreenViewModel(
    private val coroutinesScope: CoroutineScope,
    private val interactor: HomeScreenInteractor
) : BaseViewModel<HomeUIState>() {

    private val _tradingLog = MutableStateFlow<List<String>>(emptyList())
    val tradingLog: StateFlow<List<String>> = _tradingLog.asStateFlow()

    private val _tickerList = MutableStateFlow<List<Ticker>>(emptyList())
    val tickerList: StateFlow<List<Ticker>> = _tickerList.asStateFlow()

    private val _alertTriggerList = MutableStateFlow<List<AlertTrigger>>(emptyList())
    val alertTriggerList: StateFlow<List<AlertTrigger>> = _alertTriggerList.asStateFlow()

    private val _alertEventList = MutableStateFlow<List<AlertEvent>>(emptyList())
    val alertEventList: StateFlow<List<AlertEvent>> = _alertEventList.asStateFlow()

    private val _alertSettingsUIState = MutableStateFlow(AlertSettingsUIState.Success(AlertCondition("BTCUSDT")))
    val alertSettingsUIState: StateFlow<AlertSettingsUIState> = _alertSettingsUIState.asStateFlow()

    override fun initUIState(): HomeUIState = HomeUIState.Empty

    init {
        observeNetworkConnection()
        // observeLog()
        observeAlerts()
    }

    fun updateAlertCondition(alertCondition: AlertCondition) {

        _alertSettingsUIState.value = AlertSettingsUIState.Success(alertCondition)
        println("::updateAlertCondition")
    }

    fun saveAlertCondition() {
        //TODO: update existing or add a new one
    }

    private fun observeLog() {
        coroutinesScope.launch(Dispatchers.IO) {

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


    private fun observeAlerts() {
        //Alert triggers list
        fakeAlertTriggersList()
            .let { list ->
                _alertTriggerList.update { list }
            }

        //Alert events list
        fakeAlertEventsList().let { list ->
            _alertEventList.update { list }
        }

        //TODO: Show new alerts as pop-up somehow
    }

    private fun observeNetworkConnection() {
        coroutinesScope.launch {
            interactor.getServerTime().collectLatest { timeMs ->
                _uiState.update {
                    HomeUIState.Success(timeMs)
                }
            }
        }
    }

    private fun getTime(): Long = Calendar.getInstance(Locale.getDefault()).timeInMillis
}