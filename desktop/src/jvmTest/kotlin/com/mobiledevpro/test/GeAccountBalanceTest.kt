package com.mobiledevpro.test

import com.mobiledevpro.network.api.getAccountBalance
import com.mobiledevpro.network.di.networkModule
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GeAccountBalanceTest : KoinTest {

    private lateinit var httpClient: HttpClient

    @BeforeTest
    fun setup() {
        startKoin {
            modules(networkModule)
        }

        httpClient = getKoin().get<HttpClient>()
    }

    @Test
    fun getBalance() = runTest {
        val resp: HttpResponse = httpClient.getAccountBalance()

        println("Resp: ${resp.body<String>()}")

        assertEquals(resp.status, HttpStatusCode.OK, "${resp.status} | ${resp.body<String>()}")
    }

}