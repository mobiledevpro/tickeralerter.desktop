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
import com.mobiledevpro.alert.settings.view.state.AlertSettingsUIState
import com.mobiledevpro.alert.triggers.domain.interactor.AlertTriggersInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.update

/**
 * View Model for Alert setting dialog box
 *
 * Created on Jul 21, 2023.
 *
 */

class AlertSettingsViewModel(
    private val coroutineScope: CoroutineScope,
    private val interactor: AlertTriggersInteractor,
) : BaseViewModel<AlertSettingsUIState>() {

    override fun initUIState(): AlertSettingsUIState = AlertSettingsUIState.Hidden

    /**
     * It's called when the user taps gear icon in Alerts list
     */
    fun onChange(trigger: AlertTrigger) {
        _uiState.update {
            AlertSettingsUIState.Visible(
                trigger,
                //TODO: it's to debug. Get WatchList from a watchlist interactor
                fakeTickerListFirst()
                    .find { it.symbol == trigger.symbol }
                    ?.let { listOf(it) }
                    ?: emptyList<Ticker>()
            )
        }
    }

    /**
     * It's called on adding a new one Alert
     */
    fun onAddNew() {
        //TODO it should select a Ticker from the watchlist and find either first or selecter ticker
        val tickerList = fakeTickerListFirst()

        val newTrigger = AlertTrigger(
            0,
            "BTCUSDT",
            "1h",
            AlertSettings(
                conditionSource = ConditionSource.TICKER_PRICE,
                conditionType = ConditionType.CROSSING_UP,
                conditionTarget = ConditionTarget.PRICE,
                targetPrice = 33_000.00
            ),
            active = false
        )



        _uiState.update {
            AlertSettingsUIState.Visible(newTrigger, tickerList.subList(0, 3))
        }
    }

    fun onSave(trigger: AlertTrigger) {
        println("::TRIGGER SAVE ${trigger.title()}")

        onClose()
    }

    fun onClose() {
        _uiState.update { AlertSettingsUIState.Hidden }
    }

}