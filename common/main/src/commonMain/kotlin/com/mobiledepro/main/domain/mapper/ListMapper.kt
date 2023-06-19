package com.mobiledepro.main.domain.mapper

import com.mobiledevpro.database.CandleEntry
import com.mobiledevpro.database.TickerEntry
import com.mobiledevpro.database.WatchlistEntry

fun List<Any>.toDomain(): List<Any> =
    mapTo(ArrayList<Any>()) {
        when (it) {
            is TickerEntry -> (it as TickerEntry).toDomain()
            is WatchlistEntry -> (it as WatchlistEntry).toDomain()
            is CandleEntry -> (it as CandleEntry).toDomain()
            else -> throw RuntimeException("Mapping error for $it .toDomain()")
        }
    }