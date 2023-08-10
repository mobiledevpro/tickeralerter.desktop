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
package com.mobiledevpro.alert.triggers.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.mobiledevpro.database.AlertTriggerEntry
import com.mobiledevpro.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

/**
 *
 * Created on Jul 21, 2023.
 *
 */

class ImplAlertTriggersRepository(
    private val database: AppDatabase
) : AlertTriggersRepository {

    override fun getListLocal(): Flow<List<AlertTriggerEntry>> =
        database.alertTriggerQueries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)

    override suspend fun getLocal(timeCreated: Long): AlertTriggerEntry? =
        database.alertTriggerQueries.select(timeCreated)
            .executeAsOneOrNull()

    override suspend fun addLocal(entry: AlertTriggerEntry) {
        //check entry is exists
        val isExist = database.alertTriggerQueries.selectIsExist(entry.timeCreatedAt)
            .executeAsOne() > 0

        if (!isExist)
            insert(entry)
    }

    override suspend fun updateLocal(entry: AlertTriggerEntry): Boolean = insert(entry)

    override suspend fun removeLocal(entry: AlertTriggerEntry) {
        database.alertTriggerQueries.deleteItem(entry.timeCreatedAt)
    }

    private fun insert(entry: AlertTriggerEntry): Boolean {
        database.alertTriggerQueries.insertItem(entry)
        return true
    }

}