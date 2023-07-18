package com.mobiledepro.main.view

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<State : UiState> {

    protected val _uiState: MutableStateFlow<State> = MutableStateFlow(initUIState())
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    abstract fun initUIState(): State
}