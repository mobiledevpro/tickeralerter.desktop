package common.domain.interactor

import kotlinx.coroutines.flow.Flow

interface MainScreenInteractor {
    fun getServerTime(): Flow<Long>
}