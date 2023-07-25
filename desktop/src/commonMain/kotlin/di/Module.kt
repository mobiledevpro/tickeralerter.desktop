package di

import com.mobiledevpro.chart.di.featureChartModule
import com.mobiledevpro.home.di.featureHomeModule
import com.mobiledevpro.tickerlist.di.featureTickerListModule
import com.mobiledevpro.watchlist.di.featureWatchlistModule

val featureModules = listOf(
    featureHomeModule,
    featureWatchlistModule,
    featureChartModule,
    featureTickerListModule
)