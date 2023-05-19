package network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.InetAddress
import java.net.UnknownHostException

@OptIn(ObsoleteCoroutinesApi::class)
fun isInternetAvailable(): Flow<Boolean> = flow {

    ticker(3000, 0)
        .consumeEach {
            val online = try {
                val address = InetAddress.getByName("www.google.com")
                address != null && !address.equals("")
            } catch (e: UnknownHostException) {
                false
            }

            emit(online)
        }

}.flowOn(Dispatchers.IO)