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
package com.mobiledevpro.account.domain.interactor

import com.mobiledepro.main.domain.mapper.toDomain
import com.mobiledepro.main.domain.mapper.toLocal
import com.mobiledepro.main.domain.model.WalletBalance
import com.mobiledevpro.account.data.repository.AccountRepository
import com.mobiledevpro.network.model.WalletBalanceRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

/**
 *
 * Created on Aug 14, 2023.
 *
 */

class ImplAccountInteractor(
    private val repository: AccountRepository
) : AccountInteractor {

    override fun getBalance(): Flow<List<WalletBalance>> =
        repository.getBalanceLocal()
            .map { it.toDomain<WalletBalance>() }
            .flowOn(Dispatchers.IO)

    override suspend fun syncAccountData() {

        repository.subscribeOnBalanceUpdateRemote()
            .combine(repository.getBalanceRemote()) { wsBalance: List<WalletBalanceRemote>,
                                                      apiBalance: List<WalletBalanceRemote> ->
                println("::ACCOUNT BALANCE: ${wsBalance.isNotEmpty()} | ${apiBalance.isNotEmpty()}")
                apiBalance.ifEmpty { wsBalance }
            }
            .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
            .flowOn(Dispatchers.IO)
            .map {
                //TODO: cache locally
                it.forEach { balance ->
                    println("::ACCOUNT BALANCE: $balance")
                }

                it.toLocal().let { entryList ->
                    repository.cacheBalanceLocal(entryList)
                }

            }
            .collect()
    }
}