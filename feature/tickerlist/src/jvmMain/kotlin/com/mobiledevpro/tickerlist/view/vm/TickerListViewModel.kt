package com.mobiledevpro.tickerlist.view.vm

import com.mobiledepro.main.view.BaseViewModel
import com.mobiledevpro.tickerlist.domain.interactor.TickerListInteractor
import com.mobiledevpro.tickerlist.view.state.TickerListUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TickerListViewModel(
    private val coroutineScope: CoroutineScope,
    private val interactor: TickerListInteractor
) : BaseViewModel<TickerListUIState>() {

    override fun initUIState(): TickerListUIState = TickerListUIState.Empty

    init {
        observeTickerList()
    }

    fun tickerListSearch(value: String) {
        coroutineScope.launch {
            if (value.isEmpty())
                interactor.clearTickerListSearch()
            else
                interactor.setTickerListSearch(value)
        }
    }

    private fun observeTickerList() {
        _uiState.update { TickerListUIState.Loading }

        coroutineScope.launch {
            interactor.syncTickerList()
        }

        coroutineScope.launch {
            interactor.getTickerList().collectLatest { list ->
                println("Get local ticker list: ${list.size}")
                if (list.isNotEmpty())
                    _uiState.update {
                        TickerListUIState.Success(list)
                    }
            }
        }
    }
}