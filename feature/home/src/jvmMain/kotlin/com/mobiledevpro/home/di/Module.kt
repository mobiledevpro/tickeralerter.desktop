package com.mobiledevpro.home.di

import com.mobiledevpro.home.domain.interactor.HomeScreenInteractor
import com.mobiledevpro.home.domain.interactor.ImplHomeScreenInteractor
import com.mobiledevpro.home.view.vm.HomeScreenViewModel
import com.mobiledevpro.tickerlist.data.repository.ImplTickerListRepository
import com.mobiledevpro.tickerlist.data.repository.TickerRepository
import com.mobiledevpro.watchlist.data.repository.ImplWatchListRepository
import com.mobiledevpro.watchlist.data.repository.WatchListRepository
import org.koin.dsl.module

val featureHomeModule = module {
    scope<HomeScreenViewModel> {
        scoped {
            HomeScreenViewModel(
                coroutinesScope = get(),
                interactor = get()
            )
        }

        scoped<HomeScreenInteractor> {
            ImplHomeScreenInteractor(
                tickersRepository = get(),
                watchListRepository = get()
            )
        }

        scoped<TickerRepository> {
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