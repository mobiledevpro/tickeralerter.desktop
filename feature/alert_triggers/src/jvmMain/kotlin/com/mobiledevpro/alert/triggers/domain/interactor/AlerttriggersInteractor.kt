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
package com.mobiledevpro.alert.triggers.domain.interactor

import com.mobiledepro.main.domain.model.AlertTrigger
import kotlinx.coroutines.flow.Flow

/**
 *
 * Created on Jul 21, 2023.
 *
 */

interface AlertTriggersInteractor {

    fun getTriggersList(): Flow<List<AlertTrigger>>

    suspend fun getTrigger(timeCreated: Long): AlertTrigger?

    suspend fun saveTrigger(trigger: AlertTrigger)

    suspend fun deleteTrigger(timeCreated: Long)
}