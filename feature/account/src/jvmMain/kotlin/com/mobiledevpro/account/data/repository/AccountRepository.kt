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
package com.mobiledevpro.account.data.repository

import com.mobiledevpro.database.WalletBalanceEntry
import com.mobiledevpro.network.model.WalletBalanceRemote
import kotlinx.coroutines.flow.Flow

/**
 *
 * Created on Aug 14, 2023.
 *
 */

interface AccountRepository {

    fun getBalanceLocal(): Flow<List<WalletBalanceEntry>>

    fun subscribeOnBalanceUpdateRemote(): Flow<List<WalletBalanceRemote>>

    suspend fun getBalanceRemote(): Flow<List<WalletBalanceRemote>>

    suspend fun cacheBalanceLocal(balance: List<WalletBalanceEntry>)
}