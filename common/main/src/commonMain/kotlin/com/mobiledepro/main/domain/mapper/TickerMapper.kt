package com.mobiledepro.main.domain.mapper

import com.mobiledepro.main.domain.model.Ticker
import com.mobiledevpro.database.TickerEntry
import com.mobiledevpro.database.WatchlistEntry
import com.mobiledevpro.network.model.TickerRemote

fun TickerRemote.toLocal(): TickerEntry =
    TickerEntry(
        symbol = symbol,
        baseAsset = baseAsset,
        contractType = contractType,
        lastPrice = 0.0,
        priceChange = 0.0,
        priceChangePercent = 0.0
    )

fun TickerEntry.toDomain(): Ticker =
    Ticker(
        symbol, baseAsset, contractType, lastPrice, priceChange, priceChangePercent
    )

fun WatchlistEntry.toDomain(): Ticker =
    Ticker(
        symbol,
        "",
        "",
        String.format("%.4f", lastPrice).toDouble(),
        String.format("%.4f", priceChange).toDouble(),
        String.format("%.4f", priceChangePercent).toDouble()
    )


fun Ticker.toWatchlistLocal(): WatchlistEntry =
    WatchlistEntry(
        symbol = symbol,
        lastPrice = 0.0,
        priceChange = 0.0,
        priceChangePercent = 0.0
    )


fun List<TickerRemote>.toLocal(): List<TickerEntry> =
    mapTo(ArrayList<TickerEntry>(), TickerRemote::toLocal)

