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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ImplMainScreenInteractor(
    private val tickersRepository: TickerRepository
) : MainScreenInteractor {

    override suspend fun syncTickerList() {
        withContext(Dispatchers.IO) {
            tickersRepository.getTickerListRemote()
                .map { it.toLocal() }
                .also {
                    tickersRepository.cacheTickerListLocal(it)
                }
        }
    }

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

    override fun getTickerList(): Flow<List<Ticker>> =
        tickersRepository.getTickerListLocal()
            .map { it.toDomain() }
            .flowOn(Dispatchers.IO)

}