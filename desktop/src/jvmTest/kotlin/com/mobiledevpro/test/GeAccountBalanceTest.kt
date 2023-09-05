package com.mobiledevpro.test

import com.mobiledevpro.network.api.getAccountBalance
import com.mobiledevpro.network.di.networkModule
import com.mobiledevpro.network.util.SignatureGenerator
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import java.util.*
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
        val timeStamp = Date().time
        val secretKey = "251fc3fa22f1eb429bdeae059d624c29579cd973dd7122c3517d0a25077c7061"

        //http query params
        val queryMap = mapOf(
            Pair("timestamp", timeStamp),
            Pair("recvWindow", "5000")
        )
        println(queryMap)

        val signature = SignatureGenerator.encode(queryMap, secretKey)

        println("Signature: $signature")

        val resp: HttpResponse = httpClient.getAccountBalance(
            timeStamp,
            signature
        )

        println("Resp: ${resp.body<String>()}")

        assertEquals(resp.status, HttpStatusCode.OK, "${resp.status} | ${resp.body<String>()}")
    }

}