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

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.mobiledevpro.database.AppDatabase
import com.mobiledevpro.database.WalletBalanceEntry
import com.mobiledevpro.network.SocketClient
import com.mobiledevpro.network.api.BinanceSocket
import com.mobiledevpro.network.wsSubscribe
import io.ktor.client.*
import io.ktor.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 *
 * Created on Aug 14, 2023.
 *
 */
class ImplAccountRepository(
    private val database: AppDatabase,
    private val httpClient: HttpClient,
    private val socketClient: SocketClient,
) : AccountRepository {

    override fun getBalances(): Flow<List<WalletBalanceEntry>> =
        database.walletBalanceQueries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)

    //TODO: call Http to get the current account info
    //TODO: then call http to get listen key for WSS
    //TODO: and then subscribe to web socket
    override fun subscribeOnAccountRemote(): Flow<String> =
        BinanceSocket.Request(
            method = BinanceSocket.Method.REQUEST,
            params = listOf("@account", "@balance").toTypedArray(),
            id = 2
        ).let { request ->
            println("::ACCOUNT REQUEST")
            socketClient.wsSubscribe(request, "/e8pNFdn9PXyTcLS3RuEbMAIsD2NNkTGH2tAilj0bdP30Z6yKc3RZ3nU9Lkqu7ehr")
        }.map {
            println(":: Thread ${Thread.currentThread().name} :: SOCKET 2 :: \n${it.readText()}")
            it.toString()
        }

}