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
package com.mobiledevpro.alert.triggers.view.vm

import com.mobiledepro.main.domain.model.*
import com.mobiledepro.main.util.toLog
import com.mobiledepro.main.view.BaseViewModel
import com.mobiledevpro.alert.triggers.domain.interactor.AlertTriggersInteractor
import com.mobiledevpro.alert.triggers.view.state.AlertTriggersUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * VM for Trigger lis / Alerts list
 *
 * Created on Jul 21, 2023.
 *
 */

class AlertTriggersViewModel(
    private val coroutineScope: CoroutineScope,
    private val interactor: AlertTriggersInteractor
) : BaseViewModel<AlertTriggersUIState>() {

    override fun initUIState(): AlertTriggersUIState = AlertTriggersUIState.Empty

    init {
        observeTriggerList()

        //create fake triggers to test
        addFakeTriggers()
    }

    fun onDelete(trigger: AlertTrigger) {
        //TODO: delete locally
    }

    fun onChangeStatus(trigger: AlertTrigger) {
        onSave(trigger)
    }

    private fun observeTriggerList() {
        //Get watchlist saved locally
        coroutineScope.launch {
            interactor.getTriggersList().catch { throwable ->
                println("::ERROR ${throwable.toLog<AlertTriggersViewModel>()}")
            }.collectLatest { list ->
                _uiState.update {
                    if (list.isEmpty())
                        AlertTriggersUIState.Empty
                    else
                        AlertTriggersUIState.Success(list)
                }

                list.forEach {
                    println("::ALERT TRIGGER: time ${it.timeCreated} | ${it.title()} | Active ${it.status}")
                }
            }
        }

        /*coroutineScope.launch {
            delay(2000)
            fakeAlertTriggersList().also { list ->
                _uiState.update {
                    AlertTriggersUIState.Success(list)
                }
            }
        }

         */
    }

    private fun onSave(trigger: AlertTrigger) {
        coroutineScope.launch {
            interactor.saveTrigger(trigger)
        }
    }

    private fun addFakeTriggers() {
        AlertTrigger(
            1688180400000,
            "BTCUSDT",
            null,
            AlertSettings(
                conditionSource = ConditionSource.TICKER_PRICE,
                conditionType = ConditionType.CROSSING_UP,
                conditionTarget = ConditionTarget.PRICE,
                targetPrice = 31_000.00
            ),
            status = AlertStatus.ACTIVE
        ).also(::onSave)

        AlertTrigger(
            1688182200000,
            "ETHUSDT",
            null,
            AlertSettings(
                conditionSource = ConditionSource.TICKER_PRICE,
                conditionType = ConditionType.CROSSING_DOWN,
                conditionTarget = ConditionTarget.PRICE,
                targetPrice = 1_800.00
            ),
            status = AlertStatus.PAUSED
        ).also(::onSave)
    }

}