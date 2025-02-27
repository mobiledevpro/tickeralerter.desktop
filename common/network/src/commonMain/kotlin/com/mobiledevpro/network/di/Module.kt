package com.mobiledevpro.network.di

import com.mobiledevpro.network.BinanceHTTPClientFactory
import com.mobiledevpro.network.BinanceSocketClientFactory
import com.mobiledevpro.network.BuildKonfig
import com.mobiledevpro.network.SocketClient
import io.ktor.client.*
import org.koin.dsl.module

val networkModule = module {

    single<HttpClient> {
        BinanceHTTPClientFactory.build(
            isTestNet = BuildKonfig.isTestnet,
            apiKey = BuildKonfig.apiKey
        )
    }

    factory<SocketClient> {
        BinanceSocketClientFactory.build(
            isTestnet = BuildKonfig.isTestnet
        )
    }
}