package com.mobiledepro.main.domain.mapper

import com.mobiledevpro.database.WatchlistEntry
import com.mobiledevpro.network.api.BinanceSocket
import com.mobiledevpro.network.jsonFormat
import com.mobiledevpro.network.model.WatchlistSymbolRemote
import io.ktor.websocket.*
import kotlinx.serialization.decodeFromString

fun List<String>.toSocketRequest(method: BinanceSocket.Method, streamType: String): BinanceSocket.Request =
    mapTo(ArrayList<String>()) { symbol ->
        "${symbol.lowercase()}@$streamType"
    }.toTypedArray()
        .let { params ->
            BinanceSocket.Request(
                method = method,
                params = params,
                id = 1
            )
        }

fun WatchlistEntry.toSocketRequest(method: BinanceSocket.Method, streamType: String): BinanceSocket.Request =
    listOf<WatchlistEntry>(this)
        .mapTo(ArrayList<String>()) { ticker ->
            "${ticker.symbol.lowercase()}@$streamType"
        }.toTypedArray()
        .let { params ->
            BinanceSocket.Request(
                method = method,
                params = params,
                id = 1
            )
        }

fun Frame.Text.toWatchlistSymbol(): WatchlistSymbolRemote =
    try {
        readText()
            .let(jsonFormat::decodeFromString)
    } catch (e: Exception) {
        println(e.localizedMessage)
        WatchlistSymbolRemote("")
    }

fun WatchlistSymbolRemote.toLocal(): WatchlistEntry? =
    if (this.symbol.isNotEmpty())
        WatchlistEntry(
            symbol = symbol,
            lastPrice = lastPrice,
            priceChange = priceChange,
            priceChangePercent = priceChangePercent
        )
    else
        null