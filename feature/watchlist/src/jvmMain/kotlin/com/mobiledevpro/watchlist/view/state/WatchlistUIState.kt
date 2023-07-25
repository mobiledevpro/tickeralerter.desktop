package com.mobiledevpro.watchlist.view.state

import com.mobiledepro.main.domain.model.Ticker
import com.mobiledepro.main.view.UiState

sealed interface WatchlistUIState : UiState {
    class Success(val list: List<Ticker>) : WatchlistUIState

    object Empty : WatchlistUIState
}

fun WatchlistUIState.getSuccess(): List<Ticker> =
    when (this) {
        is WatchlistUIState.Success -> list
        else -> emptyList()
    }