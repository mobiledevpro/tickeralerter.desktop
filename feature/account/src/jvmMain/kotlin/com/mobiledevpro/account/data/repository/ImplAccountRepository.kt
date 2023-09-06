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
import com.mobiledepro.main.domain.mapper.toBalanceRemote
import com.mobiledevpro.database.AppDatabase
import com.mobiledevpro.database.WalletBalanceEntry
import com.mobiledevpro.network.SocketClient
import com.mobiledevpro.network.api.BinanceSocket
import com.mobiledevpro.network.api.getStreamDataKey
import com.mobiledevpro.network.api.setStreamDataKeyAlive
import com.mobiledevpro.network.model.StreamDataKeyRemote
import com.mobiledevpro.network.model.WalletBalanceRemote
import com.mobiledevpro.network.wsSubscribe
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.*

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

    override fun getBalanceLocal(): Flow<List<WalletBalanceEntry>> =
        database.walletBalanceQueries.selectNonZero()
            .asFlow()
            .mapToList(Dispatchers.IO)

    //TODO: call Http to get the current account info
    //TODO: then call http to get listen key for WSS
    //TODO: and then subscribe to web socket
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun subscribeOnBalanceUpdateRemote(): Flow<List<WalletBalanceRemote>> =
        getStreamDataKey()
            .flowOn(Dispatchers.IO)
            .map { listenKey ->
                BinanceSocket.Request(
                    method = BinanceSocket.Method.REQUEST,
                    params = listOf(
                        "$listenKey@account",
                        "$listenKey@balance"
                    ).toTypedArray(),
                    id = 2,
                    listenKey = listenKey
                )
            }.flatMapLatest { request ->
                println("::ACCOUNT REQUEST: listen key ${request.listenKey}")
                socketClient.wsSubscribe(request, "/${request.listenKey}")
            }
            .flowOn(Dispatchers.IO)
            .map {
                println(":: Thread ${Thread.currentThread().name} :: SOCKET 2 :: \n${it.readText()}")
                it.toBalanceRemote()
            }


    override fun getBalanceRemote(): List<WalletBalanceRemote> {
        TODO("Not yet implemented")
    }

    override fun cacheBalanceLocal(balance: List<WalletBalanceEntry>) {
        database.walletBalanceQueries.transaction {
            balance.forEach { item ->
                database.walletBalanceQueries.insert(item)
            }
        }
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    private fun getStreamDataKey(): Flow<String> = flow {
        getStreamDataKeyRemote().also {
            emit(it)
        }

        //Ping every 30 min to keep ListenKey alive
        ticker(delayMillis = GET_STREAM_KEY_INTERVAL_MS, initialDelayMillis = 0)
            .consumeEach {
                println("LISTEN KEY SET KEEP ALIVE")
                try {
                    setStreamDataKeyAlive()
                } catch (e: RuntimeException) {
                    println("::ERROR: setStreamDataKeyAlive: ${e.localizedMessage}")
                    getStreamDataKeyRemote().also { emit(it) }
                }

            }
    }


    /**
     * The key is valid only for 60 min
     */
    private suspend fun getStreamDataKeyRemote(): String =
        httpClient.getStreamDataKey().let {
            if (it.status != HttpStatusCode.OK)
                throw RuntimeException("Failed to get a listen key for getting account updates: ${it.status}")
            else
                it.body<StreamDataKeyRemote>().listenKey
        }

    /**
     * It should be called every 30-60 min
     */
    private suspend fun setStreamDataKeyAlive() =
        httpClient.setStreamDataKeyAlive().let {
            if (it.status != HttpStatusCode.OK)
                throw RuntimeException("Failed to set a listen key alive")

            println("LISTEN KEY SET KEEP ALIVE. status ${it.status}")
        }


    companion object {
        const val GET_STREAM_KEY_INTERVAL_MS: Long = 1_800_000 //30 min
    }
}