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

import com.mobiledepro.main.domain.mapper.toDomain
import com.mobiledepro.main.domain.mapper.toLocal
import com.mobiledepro.main.domain.model.AlertStatus
import com.mobiledepro.main.domain.model.AlertTrigger
import com.mobiledevpro.alert.triggers.data.repository.AlertTriggersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 *
 * Created on Jul 21, 2023.
 *
 */

class ImplAlertTriggersInteractor(
    private val repository: AlertTriggersRepository
) : AlertTriggersInteractor {

    override fun getTriggersList(): Flow<List<AlertTrigger>> =
        repository.getListLocal()
            .map { it.toDomain<AlertTrigger>() }
            .flowOn(Dispatchers.IO)

    override suspend fun getTrigger(timeCreated: Long): AlertTrigger? =
        withContext(Dispatchers.IO) {
            repository.getLocal(timeCreated)
                ?.toDomain()
        }

    override suspend fun saveTrigger(trigger: AlertTrigger) {
        withContext(Dispatchers.IO) {
            println("::SAVE TRIGGER: time ${trigger.timeCreated} | ${trigger.title()}")
            if (trigger.isNew())
                trigger
                    .let {
                        it.apply { this.status = AlertStatus.ACTIVE }
                    }
                    .toLocal()
                    .also { entry -> repository.addLocal(entry) }
            else
                trigger.toLocal()
                    .also { entry -> repository.updateLocal(entry) }
        }
    }

    override suspend fun deleteTrigger(timeCreated: Long) {
        withContext(Dispatchers.IO) {
            repository.deleteLocal(timeCreated)
        }
    }
}