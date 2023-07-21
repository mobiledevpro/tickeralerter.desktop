package com.mobiledepro.main.domain.model

sealed interface AlertSettingsUIState {
    class Success(val alertCondition: AlertSettings) : AlertSettingsUIState
}