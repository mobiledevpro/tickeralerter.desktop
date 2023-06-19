package com.mobiledepro.main.domain.mapper

import com.mobiledepro.main.domain.model.Candle
import com.mobiledevpro.database.CandleEntry
import com.mobiledevpro.network.model.CandleRemote
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.longOrNull

fun CandleEntry.toDomain(): Candle =
    Candle(
        openTime = openTime,
        closeTime = closeTime,
        priceOpen = priceOpen,
        priceClose = priceClose,
        priceLow = priceLow,
        priceHigh = priceHigh,
        volume = volume
    )

fun CandleRemote.toLocal(symbol: String, timeFrame: String): CandleEntry =
    CandleEntry(
        symbol = symbol,
        timeFrame = timeFrame,
        openTime = openTime,
        closeTime = closeTime,
        priceOpen = priceOpen,
        priceClose = priceClose,
        priceLow = priceLow,
        priceHigh = priceHigh,
        volume = volume
    )

fun JsonArray.toCandleList(): List<CandleRemote> {
    val list = mutableListOf<CandleRemote>()

    this.forEach { elem ->
        if (elem is JsonArray)
            CandleRemote(
                openTime = elem[0].toLong(),
                closeTime = elem[6].toLong(),
                priceOpen = elem[1].toDouble(),
                priceClose = elem[4].toDouble(),
                priceLow = elem[3].toDouble(),
                priceHigh = elem[2].toDouble(),
                volume = elem[5].toDouble()
            ).also(list::add)
    }

    return list
}

fun Any.toDouble(): Double =
    when (this) {
        is JsonPrimitive -> this.doubleOrNull ?: 0.0
        else -> throw RuntimeException("Value $this cannot be converted to Double")
    }

fun Any.toLong(): Long =
    when (this) {
        is JsonPrimitive -> this.longOrNull ?: 0L
        else -> throw RuntimeException("Value $this cannot be converted to Long")
    }