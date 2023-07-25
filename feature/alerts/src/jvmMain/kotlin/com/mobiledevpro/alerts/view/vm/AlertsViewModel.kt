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
package com.mobiledevpro.alerts.view.vm

import com.mobiledepro.main.view.BaseViewModel
import com.mobiledevpro.alerts.domain.interactor.AlertsInteractor
import com.mobiledevpro.alerts.view.state.AlertsUIState
import kotlinx.coroutines.CoroutineScope

/**
 *
 * Created on Jul 21, 2023.
 *
 */

class AlertsViewModel(
    private val coroutineScope: CoroutineScope,
    private val interactor: AlertsInteractor
) : BaseViewModel<AlertsUIState>() {

    override fun initUIState(): AlertsUIState = AlertsUIState.Empty

}