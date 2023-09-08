package com.mobiledepro.main.domain.mapper

import com.mobiledepro.main.domain.model.WalletBalance
import com.mobiledevpro.database.WalletBalanceEntry
import com.mobiledevpro.network.jsonFormat
import com.mobiledevpro.network.model.AccountWSBalanceRemote
import com.mobiledevpro.network.model.AccountWSResponse
import com.mobiledevpro.network.model.WalletBalanceRemote
import io.ktor.websocket.*

fun Frame.Text.toBalanceRemote(): List<WalletBalanceRemote> =
    try {
        val obj: AccountWSResponse = readText()
            .let(jsonFormat::decodeFromString)
        /*
                when (obj) {
                    is AccountWSResponse -> obj.getBalances().toBalanceRemote()
                    else -> throw RuntimeException("Cannot convert WS text into Balance object")
                }

         */

        obj.getBalances().toBalanceRemote()

    } catch (e: Exception) {
        println("::ERROR: ${e.localizedMessage}")
        WalletBalanceRemote(
            asset = "",
            balance = 0.0,
            availableBalance = 0.0,
            unrealizedPnL = 0.0,
            updateTime = 0
        ).let { listOf(it) }
    }


fun List<AccountWSBalanceRemote>.toBalanceRemote(): List<WalletBalanceRemote> =
    mapTo(ArrayList(), AccountWSBalanceRemote::toBalanceRemote)

fun AccountWSBalanceRemote.toBalanceRemote(): WalletBalanceRemote =
    WalletBalanceRemote(
        asset = asset,
        balance = balance,
        availableBalance = availableBalance,
        unrealizedPnL = unrealizedPnL,
        updateTime = updateTime
    )


fun List<WalletBalanceRemote>.toLocal(): List<WalletBalanceEntry> =
    mapTo(ArrayList(), WalletBalanceRemote::toLocal)

fun WalletBalanceRemote.toLocal() =
    WalletBalanceEntry(
        asset = asset,
        balance = balance,
        availableBalance = availableBalance,
        unrealizedPNL = unrealizedPnL,
        updateTime = updateTime
    )


fun WalletBalanceEntry.toDomain() =
    WalletBalance(
        asset = asset,
        balance = String.format("%.4f", balance).toDouble(),
        availableBalance = String.format("%.4f", availableBalance).toDouble(),
        unrealizedPnL = String.format("%.4f", unrealizedPNL).toDouble(),
        updateTime = updateTime
    )