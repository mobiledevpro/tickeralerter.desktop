package common.domain.interactor

import common.domain.model.Ticker
import kotlinx.coroutines.flow.Flow

interface MainScreenInteractor {
    fun getServerTime(): Flow<Long>

    fun getTickerList(): Flow<List<Ticker>>
}