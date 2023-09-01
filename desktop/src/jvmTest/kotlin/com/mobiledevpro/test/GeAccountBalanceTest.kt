package com.mobiledevpro.test

import com.mobiledevpro.network.api.getAccountBalance
import com.mobiledevpro.network.di.networkModule
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.Signature
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
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

    @OptIn(InternalAPI::class)
    @Test
    fun getBalance() = runTest {
        val timeStamp = Date().time
        val secretKey = "251fc3fa22f1eb429bdeae059d624c29579cd973dd7122c3517d0a25077c7061"

        //http query params
        val queryMap = mapOf(Pair("timestamp", timeStamp), Pair("recvWindow", "3000"))
        println(queryMap)

        //Encoded string for GET request
        val payload = queryMap.entries.joinToString(separator = "&")

        println("Payload: $payload")

        val hmac = Mac.getInstance("HmacSHA256")
        val keySpec = SecretKeySpec(secretKey.toByteArray(StandardCharsets.UTF_8), "HmacSHA256")
        hmac.init(keySpec)

        val hashBytes = hmac.doFinal(payload.toByteArray(StandardCharsets.UTF_8))
        val hashHex = bytesToHex(hashBytes)

        println("Signature: $hashHex")

        val resp: HttpResponse = httpClient.getAccountBalance(
            timeStamp,
            hashHex
        )

        println("Resp: ${resp.body<String>()}")

        assertEquals(resp.status, HttpStatusCode.OK, "${resp.status} | ${resp.body<String>()}")
    }

    private fun bytesToHex(bytes: ByteArray): String {
        val hexChars = CharArray(bytes.size * 2)
        for (i in bytes.indices) {
            val v = bytes[i].toInt() and 0xFF
            hexChars[i * 2] = "0123456789ABCDEF"[v ushr 4]
            hexChars[i * 2 + 1] = "0123456789ABCDEF"[v and 0x0F]
        }
        return String(hexChars)
    }
}