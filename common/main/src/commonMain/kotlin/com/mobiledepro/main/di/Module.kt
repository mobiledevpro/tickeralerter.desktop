package com.mobiledepro.main.di

import com.mobiledevpro.di.databaseModule
import com.mobiledevpro.network.di.networkModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val mainModule = module {
    single<CoroutineScope> {
        CoroutineScope(SupervisorJob())
    }
}

val commonModules = listOf(
    mainModule,
    networkModule,
    databaseModule
)