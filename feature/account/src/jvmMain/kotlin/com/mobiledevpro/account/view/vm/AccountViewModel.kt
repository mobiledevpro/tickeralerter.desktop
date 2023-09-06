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
package com.mobiledevpro.account.view.vm

import com.mobiledepro.main.domain.model.fakeBalances
import com.mobiledepro.main.view.BaseViewModel
import com.mobiledevpro.account.domain.interactor.AccountInteractor
import com.mobiledevpro.account.view.state.AccountUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * View model for account info (PnL, balance)
 *
 * Created on Aug 14, 2023.
 *
 */

class AccountViewModel(
    private val coroutineScope: CoroutineScope,
    private val interactor: AccountInteractor
) : BaseViewModel<AccountUIState>() {

    override fun initUIState(): AccountUIState = AccountUIState.Loading

    init {
        observeWalletBalance()
        syncAccountData()
    }

    private fun syncAccountData() {
        coroutineScope.launch {
            interactor.syncAccountData()
        }
    }

    private fun observeWalletBalance() {
        coroutineScope.launch {
            interactor.getBalance()
                .collectLatest { list ->
                    println("::${this@AccountViewModel.javaClass.name}: ${list.size}")
                    if (list.isEmpty())
                        _uiState.update { AccountUIState.Empty }
                    else
                        _uiState.update { AccountUIState.Success(list) }
                }

            _uiState.update { AccountUIState.Success(fakeBalances) }
        }
    }
}