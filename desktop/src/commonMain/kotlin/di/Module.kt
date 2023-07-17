package di

import com.mobiledepro.main.di.commonModules
import com.mobiledevpro.watchlist.di.featureWatchlistModule

val featureModules = listOf(
    featureWatchlistModule
)
val appModules = commonModules + featureModules