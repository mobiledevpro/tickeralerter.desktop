package com.mobiledevpro.account.di

import com.mobiledevpro.account.data.repository.AccountRepository
import com.mobiledevpro.account.data.repository.ImplAccountRepository
import com.mobiledevpro.account.domain.interactor.AccountInteractor
import com.mobiledevpro.account.domain.interactor.ImplAccountInteractor
import com.mobiledevpro.account.view.vm.AccountViewModel
import org.koin.dsl.module

val featureAccountModule = module {
    scope<AccountViewModel> {
        scoped {
            AccountViewModel(
                coroutineScope = get(),
                interactor = get()
            )
        }

        scoped<AccountInteractor> {
            ImplAccountInteractor(
                repository = get()
            )
        }

        scoped<AccountRepository> {
            ImplAccountRepository(
                database = get(),
                httpClient = get(),
                socketClient = get()
            )
        }
    }
}