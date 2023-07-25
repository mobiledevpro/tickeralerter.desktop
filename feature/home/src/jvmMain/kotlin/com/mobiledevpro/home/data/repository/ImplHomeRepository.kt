package com.mobiledevpro.home.data.repository

import com.mobiledevpro.common.data.remote.model.ServerTimeRemote
import com.mobiledevpro.network.getServerTime
import io.ktor.client.*
import io.ktor.client.call.*

class ImplHomeRepository(
    private val httpClient: HttpClient
) : HomeRepository {

    override suspend fun getServerTime(): Long = httpClient.getServerTime().let { resp ->
        val body: ServerTimeRemote = resp.body()
        body.serverTime
    }

}