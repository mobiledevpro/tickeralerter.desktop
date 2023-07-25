package com.mobiledevpro.chart.di

import com.mobiledevpro.chart.data.repository.ChartRepository
import com.mobiledevpro.chart.data.repository.ImplChartRepository
import com.mobiledevpro.chart.domain.interactor.ChartInteractor
import com.mobiledevpro.chart.domain.interactor.ImplChartInteractor
import com.mobiledevpro.chart.view.vm.ChartViewModel
import org.koin.dsl.module

val featureChartModule = module {
    scope<ChartViewModel> {
        scoped {
            ChartViewModel(
                coroutineScope = get(),
                interactor = get()
            )
        }

        scoped<ChartInteractor> {
            ImplChartInteractor(
                repository = get()
            )
        }

        scoped<ChartRepository> {
            ImplChartRepository(
                database = get(),
                httpClient = get()
            )
        }
    }
}