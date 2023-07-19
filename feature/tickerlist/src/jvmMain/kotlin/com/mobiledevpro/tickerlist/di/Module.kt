package com.mobiledevpro.tickerlist.di

import com.mobiledevpro.tickerlist.data.repository.ImplTickerListRepository
import com.mobiledevpro.tickerlist.data.repository.TickerListRepository
import com.mobiledevpro.tickerlist.domain.interactor.ImplTickerListInteractor
import com.mobiledevpro.tickerlist.domain.interactor.TickerListInteractor
import com.mobiledevpro.tickerlist.view.vm.TickerListViewModel
import com.mobiledevpro.watchlist.data.repository.ImplWatchListRepository
import com.mobiledevpro.watchlist.data.repository.WatchListRepository
import org.koin.dsl.module

val featureTickerListModule = module {
    scope<TickerListViewModel> {
        scoped {
            TickerListViewModel(
                coroutineScope = get(),
                interactor = get()
            )
        }

        scoped<TickerListInteractor> {
            ImplTickerListInteractor(
                tickersRepository = get(),
                watchListRepository = get()
            )
        }

        scoped<TickerListRepository> {
            ImplTickerListRepository(
                database = get(),
                httpClient = get()
            )
        }
        scoped<WatchListRepository> {
            ImplWatchListRepository(
                database = get(),
                socketClient = get()
            )
        }
    }
}