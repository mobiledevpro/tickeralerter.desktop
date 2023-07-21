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
package com.mobiledevpro.alert.events.view.vm

import com.mobiledepro.main.view.BaseViewModel
import com.mobiledevpro.alert.events.domain.interactor.AlertEventsInteractor
import com.mobiledevpro.alert.events.view.state.AlertEventsUIState
import kotlinx.coroutines.CoroutineScope

/**
 * Class for ...
 *
 * Created on Jul 21, 2023.
 *
 */

class AlertEventsViewModel(
    private val coroutineScope: CoroutineScope,
    private val interactor: AlertEventsInteractor
) : BaseViewModel<AlertEventsUIState>() {

    override fun initUIState(): AlertEventsUIState = AlertEventsUIState.Empty

}