/*
 * Copyright 2023 | Dmitri Chernysh | https://mobile-dev.pro
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.mobiledevpro.alert.triggers.di

import com.mobiledevpro.alert.triggers.data.repository.AlertTriggersRepository
import com.mobiledevpro.alert.triggers.data.repository.ImplAlertTriggersRepository
import com.mobiledevpro.alert.triggers.domain.interactor.AlertTriggersInteractor
import com.mobiledevpro.alert.triggers.domain.interactor.ImplAlertTriggersInteractor
import com.mobiledevpro.alert.triggers.view.vm.AlertTriggersViewModel
import org.koin.dsl.module

val featureAlertTriggersModule = module {
    scope<AlertTriggersViewModel> {
        scoped {
            AlertTriggersViewModel(
                coroutineScope = get(),
                interactor = get()
            )
        }

        scoped<AlertTriggersInteractor> {
            ImplAlertTriggersInteractor(
                repository = get()
            )
        }

        scoped<AlertTriggersRepository> {
            ImplAlertTriggersRepository(
                database = get()
            )
        }
    }
}