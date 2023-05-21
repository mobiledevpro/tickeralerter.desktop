package feature.tickerlist.data.repository

import io.ktor.client.*
import io.ktor.client.call.*
import network.getServerTime
import network.model.ServerTime

class ImplTickerListRepository(
    private val httpClient: HttpClient
) : TickerRepository {

    override suspend fun getServerTime(): Long = httpClient.getServerTime().let { resp ->
        val body: ServerTime = resp.body()
        body.serverTime
    }
}