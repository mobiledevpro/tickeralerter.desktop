package com.mobiledevpro.watchlist.view.model

import com.mobiledepro.main.domain.model.Ticker

sealed interface WatchlistUIState {
    class Success(val list: List<Ticker>) : WatchlistUIState

    object Empty : WatchlistUIState
}