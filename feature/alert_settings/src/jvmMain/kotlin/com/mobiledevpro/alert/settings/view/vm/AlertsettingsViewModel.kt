/*
 * Copyright 2023 | Dmitri Chernysh | https://mobile-dev.pro
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.mobiledevpro.alert.settings.view.vm

import com.mobiledepro.main.domain.model.*
import com.mobiledepro.main.view.BaseViewModel
import com.mobiledevpro.alert.settings.domain.interactor.AlertSettingsInteractor
import com.mobiledevpro.alert.settings.view.state.AlertSettingsUIState
import com.mobiledevpro.alert.triggers.domain.interactor.AlertTriggersInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * View Model for Alert setting dialog box
 *
 * Created on Jul 21, 2023.
 *
 */

class AlertSettingsViewModel(
    private val coroutineScope: CoroutineScope,
    private val alertSettingsInteractor: AlertSettingsInteractor,
    private val alertTriggersInteractor: AlertTriggersInteractor
) : BaseViewModel<AlertSettingsUIState>() {

    override fun initUIState(): AlertSettingsUIState = AlertSettingsUIState.Hidden

    /**
     * It's called when the user taps gear icon in Alerts list
     */
    fun onEdit(trigger: AlertTrigger) {

        trigger.timeCreated?.also { timeCreated ->
            coroutineScope.launch {

                val alertTrigger: AlertTrigger = alertTriggersInteractor.getTrigger(timeCreated) ?: return@launch
                val tickerList: List<Ticker> = alertSettingsInteractor.getTickerList()

                if (tickerList.isEmpty())
                    throw RuntimeException("There are no tickers added to Watchlist")

                //No need to show the whole list of ticker, just only ticker to be edited
                val filteredTickerList: List<Ticker> =
                    tickerList
                        .find { it.symbol == trigger.symbol }
                        ?.let { listOf(it) }
                        ?: emptyList<Ticker>()

                if (filteredTickerList.isEmpty())
                    throw RuntimeException("${trigger.symbol} is not found in Watchlist")

                _uiState.update {
                    AlertSettingsUIState.Visible(alertTrigger, filteredTickerList)
                }

            }
        }
    }

    /**
     * It's called when the user wants to add a new one trigger
     */
    fun onAddNew() {
        coroutineScope.launch {
            val tickerList: List<Ticker> = alertSettingsInteractor.getTickerList()

            if (tickerList.isEmpty())
                throw RuntimeException("There are no tickers added to Watchlist")

            val newTrigger = AlertTrigger(
                0,
                tickerList.get(0).symbol,
                null,
                AlertSettings(
                    conditionSource = ConditionSource.TICKER_PRICE,
                    conditionType = ConditionType.CROSSING_UP,
                    conditionTarget = ConditionTarget.PRICE,
                    targetPrice = 33_000.00
                ),
                status = AlertStatus.ACTIVE
            )

            _uiState.update {
                AlertSettingsUIState.Visible(newTrigger, tickerList)
            }
        }

    }

    fun onSave() {

        val trigger = when (_uiState.value) {
            is AlertSettingsUIState.Visible -> (uiState.value as AlertSettingsUIState.Visible).trigger
            else -> return
        }

        println("::TRIGGER SAVE $trigger")

        coroutineScope.launch {
            alertTriggersInteractor.saveTrigger(trigger)
        }

        _uiState.update {
            AlertSettingsUIState.Hidden
        }
    }

    fun onClose() {
        _uiState.update { AlertSettingsUIState.Hidden }
    }

    fun onTriggerChanged(trigger: AlertTrigger) {
        println("::ON TRIGGER CHANGED ${trigger.title()}")

        val tickerList: List<Ticker> = when (uiState.value) {
            is AlertSettingsUIState.Visible -> (uiState.value as AlertSettingsUIState.Visible).tickerList
            else -> emptyList()
        }

        _uiState.update { AlertSettingsUIState.Visible(trigger, tickerList) }
    }
}