package com.mobiledevpro.network.di

import com.mobiledevpro.network.BinanceHTTPClientFactory
import com.mobiledevpro.network.BinanceSocketClientFactory
import com.mobiledevpro.network.SocketClient
import io.ktor.client.*
import org.koin.dsl.module

val networkModule = module {
    val isTestNet = true

    single<HttpClient> {
        BinanceHTTPClientFactory.build(
            isTestNet = isTestNet
        )
    }

    factory<SocketClient> {
        BinanceSocketClientFactory.build(
            isTestnet = isTestNet
        )
    }
}