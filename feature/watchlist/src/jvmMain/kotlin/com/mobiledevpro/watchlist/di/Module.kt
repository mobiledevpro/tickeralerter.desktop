package com.mobiledevpro.watchlist.di

import com.mobiledevpro.watchlist.data.repository.ImplWatchListRepository
import com.mobiledevpro.watchlist.data.repository.WatchListRepository
import com.mobiledevpro.watchlist.domain.interactor.ImplWatchListInteractor
import com.mobiledevpro.watchlist.domain.interactor.WatchListInteractor
import com.mobiledevpro.watchlist.view.vm.WatchlistViewModel
import org.koin.dsl.module

val featureWatchlistModule = module {
    scope<WatchlistViewModel> {
        scoped {
            WatchlistViewModel(
                scope = get(),
                interactor = get()
            )
        }

        scoped<WatchListInteractor> {
            ImplWatchListInteractor(
                watchListRepository = get()
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