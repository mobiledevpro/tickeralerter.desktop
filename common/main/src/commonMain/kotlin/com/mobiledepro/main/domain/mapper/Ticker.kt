package com.mobiledepro.main.domain.mapper

import com.mobiledepro.main.domain.model.Ticker
import com.mobiledevpro.common.data.remote.model.SymbolRemote
import com.mobiledevpro.database.TickerEntry

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

fun List<TickerEntry>.toDomain(): List<Ticker> =
    mapTo(ArrayList<Ticker>(), TickerEntry::toDomain)