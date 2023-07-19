package com.mobiledevpro.chart.view.state

import com.mobiledepro.main.domain.model.Chart
import com.mobiledepro.main.view.UiState

sealed interface ChartUIState : UiState {
    class Success(val chartData: Chart) : ChartUIState

    object Empty : ChartUIState

    object Loading : ChartUIState
}