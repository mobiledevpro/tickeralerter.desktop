package com.mobiledevpro.watchlist.di

import com.mobiledevpro.watchlist.data.repository.ImplWatchListRepository
import com.mobiledevpro.watchlist.data.repository.WatchListRepository
import com.mobiledevpro.watchlist.domain.interactor.ImplWatchListInteractor
import com.mobiledevpro.watchlist.domain.interactor.WatchListInteractor
import com.mobiledevpro.watchlist.view.vm.WatchlistViewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.getKoin

val featureWatchlistModule = module {
    scope(named(SCOPE_NAME_WATCHLIST)) {
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


fun scopeWatchlist(): Scope =
    getKoin().getOrCreateScope(
        SCOPE_ID_WATCHLIST,
        named(SCOPE_NAME_WATCHLIST)
    )


private const val SCOPE_NAME_WATCHLIST = "watchlist"
private const val SCOPE_ID_WATCHLIST = "watchlist"
