package com.mobiledepro.main.domain.mapper

import com.mobiledevpro.database.*

fun <Out> List<Any>.toDomain(): List<Out> =
    mapTo(ArrayList<Out>()) {

        when (it) {
            is TickerEntry -> it.toDomain() as Out
            is WatchlistEntry -> it.toDomain() as Out
            is CandleEntry -> it.toDomain() as Out
            is AlertTriggerEntry -> it.toDomain() as Out
            is WalletBalanceEntry -> it.toDomain() as Out
            else -> throw RuntimeException("Mapping error for $it .toDomain()")
        }
    }