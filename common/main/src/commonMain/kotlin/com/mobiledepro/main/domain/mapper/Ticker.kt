package com.mobiledepro.main.domain.mapper

import com.mobiledepro.main.domain.model.Ticker
import com.mobiledevpro.common.data.remote.model.SymbolRemote
import com.mobiledevpro.database.TickerEntry
import com.mobiledevpro.database.WatchlistEntry

fun SymbolRemote.toLocal(): TickerEntry =
    TickerEntry(
        symbol = symbol,
        baseAsset = baseAsset,
        contractType = contractType,
        lastPrice = 0.0,
        priceChange = 0.0,
        priceChangePercent = 0.0
    )

fun List<SymbolRemote>.toLocal(): List<TickerEntry> =
    mapTo(ArrayList<TickerEntry>(), SymbolRemote::toLocal)

fun TickerEntry.toDomain(): Ticker =
    Ticker(
        symbol, baseAsset, contractType, lastPrice, priceChange, priceChangePercent
    )

fun WatchlistEntry.toDomain(): Ticker =
    Ticker(
        symbol,
        "",
        "",
        lastPrice,
        priceChange,
        priceChangePercent
    )

fun List<Any>.toDomain(): List<Ticker> =
    mapTo(ArrayList<Ticker>()) {
        when (it) {
            is TickerEntry -> (it as TickerEntry).toDomain()
            is WatchlistEntry -> (it as WatchlistEntry).toDomain()
            else -> throw RuntimeException("Mapping error for $it .toDomain()")
        }
    }

fun Ticker.toWatchlistLocal(): WatchlistEntry =
    WatchlistEntry(
        symbol = symbol,
        lastPrice = 0.0,
        priceChange = 0.0,
        priceChangePercent = 0.0
    )