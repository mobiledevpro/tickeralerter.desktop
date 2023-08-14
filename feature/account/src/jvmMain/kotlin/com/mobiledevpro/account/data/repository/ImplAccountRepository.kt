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
package com.mobiledevpro

import com.mobiledevpro.account.data.repository.AccountRepository
import com.mobiledevpro.database.AppDatabase
import com.mobiledevpro.network.SocketClient
import java.net.http.HttpClient

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

}