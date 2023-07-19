package com.mobiledevpro.home.domain.interactor

import kotlinx.coroutines.flow.Flow

interface HomeScreenInteractor {

    fun getServerTime(): Flow<Long>
}