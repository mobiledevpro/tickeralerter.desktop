package com.mobiledevpro.common.domain.interactor

import com.mobiledevpro.common.domain.model.Ticker
import com.mobiledevpro.common.mapper.toDomain
import com.mobiledevpro.common.mapper.toLocal
import com.mobiledevpro.feature.tickerlist.data.repository.TickerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ImplMainScreenInteractor(
    private val tickersRepository: TickerRepository
) : MainScreenInteractor {

    @OptIn(ObsoleteCoroutinesApi::class)
    override fun getServerTime(): Flow<Long> = flow {

        ticker(3000, 0)
            .consumeEach {
                try {
                    tickersRepository.getServerTime().also { timeMs ->
                        emit(timeMs)
                    }
                } catch (e: Exception) {
                    println("Exception: ${e.localizedMessage}")
                    emit(0)
                }

            }

    }.flowOn(Dispatchers.IO)

    override fun getTickerList(): Flow<List<Ticker>> = flow {

        //TODO: for debugging
        tickersRepository.getTickerListRemote()
            .map { it.toLocal() }
            .map { it.toDomain() }
            .also { emit(it) }
    }.flowOn(Dispatchers.IO)

}