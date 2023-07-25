package com.mobiledevpro.tickerlist.view.state

import com.mobiledepro.main.domain.model.Ticker
import com.mobiledepro.main.view.UiState

sealed interface TickerListUIState : UiState {
    class Success(val list: List<Ticker>) : TickerListUIState

    object Empty : TickerListUIState

    object Loading : TickerListUIState
}