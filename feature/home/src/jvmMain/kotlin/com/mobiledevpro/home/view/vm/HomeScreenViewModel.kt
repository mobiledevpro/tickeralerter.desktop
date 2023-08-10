package com.mobiledevpro.home.view.vm

import com.mobiledepro.main.domain.model.AlertEvent
import com.mobiledepro.main.domain.model.fakeAlertEventsList
import com.mobiledepro.main.view.BaseViewModel
import com.mobiledevpro.home.domain.interactor.HomeScreenInteractor
import com.mobiledevpro.home.view.state.HomeUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val coroutinesScope: CoroutineScope,
    private val interactor: HomeScreenInteractor
) : BaseViewModel<HomeUIState>() {

    private val _alertEventList = MutableStateFlow<List<AlertEvent>>(emptyList())
    val alertEventList: StateFlow<List<AlertEvent>> = _alertEventList.asStateFlow()

    override fun initUIState(): HomeUIState = HomeUIState.Empty

    init {
        observeNetworkConnection()
        // observeLog()
        observeAlerts()
    }

    private fun observeAlerts() {

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

}