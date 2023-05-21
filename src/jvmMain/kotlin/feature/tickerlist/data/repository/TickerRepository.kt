package feature.tickerlist.data.repository

interface TickerRepository {
    suspend fun getServerTime(): Long
}