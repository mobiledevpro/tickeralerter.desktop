package com.mobiledevpro.home.domain.interactor

import com.mobiledevpro.home.data.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ImplHomeScreenInteractor(
    private val homeRepository: HomeRepository
) : HomeScreenInteractor {

    @OptIn(ObsoleteCoroutinesApi::class)
    override fun getServerTime(): Flow<Long> = flow {

        ticker(3000, 0)
            .consumeEach {
                try {
                    homeRepository.getServerTime().also { timeMs ->
                        emit(timeMs)
                    }
                } catch (e: Exception) {
                    println("Exception: ${e.localizedMessage}")
                    emit(0)
                }

            }

    }.flowOn(Dispatchers.IO)
}