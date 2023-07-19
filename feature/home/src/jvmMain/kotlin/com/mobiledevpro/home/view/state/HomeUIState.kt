package com.mobiledevpro.home.view.state

import com.mobiledepro.main.view.UiState

sealed interface HomeUIState : UiState {
    class Success(val serverTimeMs: Long) : HomeUIState

    object Empty : HomeUIState
}