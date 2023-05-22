package common.mapper

import common.data.local.model.TickerLocal
import common.data.remote.model.SymbolRemote
import common.domain.model.Ticker

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
