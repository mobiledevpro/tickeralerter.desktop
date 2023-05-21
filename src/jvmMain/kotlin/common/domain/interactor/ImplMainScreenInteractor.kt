package common.domain.interactor

import feature.tickerlist.data.repository.TickerRepository
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

        ticker(5000, 0)
            .consumeEach {
                println("Get time")
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
}