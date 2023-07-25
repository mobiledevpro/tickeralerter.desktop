package com.mobiledepro.main.domain.model

sealed interface AlertSettingsUIState {
    class Success(val trigger: AlertTrigger) : AlertSettingsUIState

    object Empty : AlertSettingsUIState
}