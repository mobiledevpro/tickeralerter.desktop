package di

import com.mobiledevpro.alert.settings.di.featureAlertSettingsModule
import com.mobiledevpro.alert.triggers.di.featureAlertTriggersModule
import com.mobiledevpro.chart.di.featureChartModule
import com.mobiledevpro.home.di.featureHomeModule
import com.mobiledevpro.tickerlist.di.featureTickerListModule
import com.mobiledevpro.watchlist.di.featureWatchlistModule

val featureModules = listOf(
    featureHomeModule,
    featureWatchlistModule,
    featureChartModule,
    featureTickerListModule,
    featureAlertTriggersModule,
    featureAlertSettingsModule
)