package com.mobiledevpro.network

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


object BinanceHTTPClientFactory {

    fun build(): HttpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 10000
            socketTimeoutMillis = 10000

        }
        install(HttpRequestRetry) {
            maxRetries = 100
            retryIf { _, response ->
                !response.status.isSuccess()
            }

            retryOnExceptionOrServerErrors(maxRetries)

            //Wait before the next try
            delayMillis { retry ->
                retry * 30000L
            }
        }

        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = TEST_URL
                path("fapi/v1/")
            }

            header("X-MBX-APIKEY", KEY)
        }
    }


    const val TEST_URL = "testnet.binancefuture.com"
    const val KEY = "a8ca86b682a36b97f7fe679d074c7e4e85495ca2f58d3c7c2870ffec77f85a4b"

}

suspend fun HttpClient.getServerTime(): HttpResponse =
    get("time")


suspend fun HttpClient.getExchangeInfo(): HttpResponse =
    get("exchangeInfo")


suspend fun HttpClient.getChart(symbol: String, timeFrame: String): HttpResponse =
    get("klines") {
        url {
            parameters.append("symbol", symbol)
            parameters.append("interval", timeFrame)
        }
    }
