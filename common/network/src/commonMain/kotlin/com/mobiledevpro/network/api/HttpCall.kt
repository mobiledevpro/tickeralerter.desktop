package com.mobiledevpro.network.api

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

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

suspend fun HttpClient.getAccountBalance(timeStamp : Long, signature : String): HttpResponse =
    get("v2/balance") {
        url {
            parameters.append("timestamp", timeStamp.toString())
            parameters.append("signature", signature)
            parameters.append("recvWindow", "3000")
        }
    }
