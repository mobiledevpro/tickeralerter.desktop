package com.mobiledevpro.network.api

import com.mobiledevpro.network.BuildKonfig
import com.mobiledevpro.network.util.SignatureGenerator
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.util.*

suspend fun HttpClient.getServerTime(): HttpResponse =
    get("v1/time")


suspend fun HttpClient.getExchangeInfo(): HttpResponse =
    get("v1/exchangeInfo")


suspend fun HttpClient.getChart(symbol: String, timeFrame: String): HttpResponse =
    get("v1/klines") {
        url {
            parameters.append("symbol", symbol)
            parameters.append("interval", timeFrame)
            parameters.append("limit", "400")
        }
    }

suspend fun HttpClient.getAccountBalance(): HttpResponse =
    get("v2/balance") {
        val queryMap = mutableMapOf(
            Pair("timestamp", Date().time),
            Pair("recvWindow", "5000")
        )

        println("BALANCE REQUEST: $queryMap")

        val signature = SignatureGenerator.encode(queryMap, BuildKonfig.apiSecret)

        queryMap.put("signature", signature)

        url {
            queryMap.forEach { (key: String, value: Any) ->
                parameters.append(key, value.toString())
            }
        }
    }

suspend fun HttpClient.getStreamDataKey(): HttpResponse =
    post("v1/listenKey")

suspend fun HttpClient.setStreamDataKeyAlive(): HttpResponse =
    put("v1/listenKey")