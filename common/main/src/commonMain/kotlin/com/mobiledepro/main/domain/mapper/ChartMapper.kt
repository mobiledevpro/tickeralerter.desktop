package com.mobiledepro.main.domain.mapper

import com.mobiledepro.main.domain.model.Candle
import com.mobiledevpro.database.CandleEntry
import com.mobiledevpro.network.model.CandleRemote
import kotlinx.serialization.json.JsonArray

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
                openTime = elem[0].toString().toLong(),
                closeTime = elem[6].toString().toLong(),
                priceOpen = elem[1].toString().toDoubleOrNull() ?: 0.0,
                priceClose = elem[4].toString().toDoubleOrNull() ?: 0.0,
                priceLow = elem[3].toString().toDoubleOrNull() ?: 0.0,
                priceHigh = elem[2].toString().toDoubleOrNull() ?: 0.0,
                volume = elem[5].toString().toDoubleOrNull() ?: 0.0
            ).also(list::add)
    }

    return list
}