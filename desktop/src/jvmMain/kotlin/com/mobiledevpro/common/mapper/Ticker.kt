package com.mobiledevpro.common.mapper

import com.mobiledevpro.common.data.local.model.TickerLocal
import com.mobiledevpro.common.data.remote.model.SymbolRemote
import com.mobiledevpro.common.domain.model.Ticker

fun SymbolRemote.toLocal(): TickerLocal =
    TickerLocal(
        symbol = symbol,
        lastPrice = 0.0,
        priceChange = 0.0,
        priceChangePercent = 0.0
    )

fun List<SymbolRemote>.toLocal(): List<TickerLocal> =
    mapTo(ArrayList<TickerLocal>(), SymbolRemote::toLocal)

fun TickerLocal.toDomain(): Ticker =
    Ticker(
        symbol, lastPrice, priceChange, priceChangePercent
    )

fun List<TickerLocal>.toDomain(): List<Ticker> =
    mapTo(ArrayList<Ticker>(), TickerLocal::toDomain)
