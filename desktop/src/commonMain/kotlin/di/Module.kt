package di

import com.mobiledevpro.chart.di.featureChartModule
import com.mobiledevpro.watchlist.di.featureWatchlistModule

val featureModules = listOf(
    featureWatchlistModule,
    featureChartModule
)