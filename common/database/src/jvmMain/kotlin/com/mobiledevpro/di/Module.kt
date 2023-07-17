package com.mobiledevpro.di

import com.mobiledevpro.database.AppDatabase
import com.mobiledevpro.database.DriverFactory
import org.koin.dsl.module


val databaseModule = module {

    single<AppDatabase> {
        AppDatabase(DriverFactory().createDriver())
    }

    /* single<HttpClient> {
         BinanceHTTPClientFactory.build(
             isTestNet = isTestNet
         )
     }

     single<SocketClient> {
         BinanceSocketClientFactory.build(
             isTestnet = isTestNet
         )
     }

     */
}