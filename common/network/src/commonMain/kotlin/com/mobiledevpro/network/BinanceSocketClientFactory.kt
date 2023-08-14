package com.mobiledevpro.network

import com.mobiledevpro.network.api.BinanceSocket
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Socket client
 *
 * NOTE:
 * Read more https://github.com/binance/binance-spot-api-docs/blob/master/web-socket-streams.md
 */
object BinanceSocketClientFactory {

    fun build(isTestnet: Boolean): SocketClient =
        HttpClient(OkHttp) {
            install(WebSockets) {
                this.pingInterval = 5000
            }
            this.developmentMode = isTestnet
        }.let { httpClient ->
            SocketClient(httpClient, isTestnet)
        }

    const val TEST_URL = "stream.binancefuture.com"
    const val PROD_URL = "fstream.binance.com"
}

class SocketClient(
    val httpClient: HttpClient,
    val isTestNet: Boolean
) {
    fun url(): String = if (isTestNet)
        BinanceSocketClientFactory.TEST_URL
    else
        BinanceSocketClientFactory.PROD_URL
}

val jsonFormat = Json { ignoreUnknownKeys = true }

fun SocketClient.wsSubscribe(request: BinanceSocket.Request) = flow<Frame.Text> {
    httpClient.wss(host = url(), path = "/ws") {
        Json.encodeToString(request)
            .let {
                println("::WS REQUEST $it")
                it
            }
            .let(Frame::Text)
            .let {
                send(it)
            }

        while (true) {
            if (!isActive) break
            try {
                val othersMessage = incoming.receive() as? Frame.Text
                othersMessage?.let {
                    emit(it)
                }
            } catch (e: Exception) {
                println("WebSocket exception [wsSubscribe]: ${e.localizedMessage}")
            }
        }

    }
}.cancellable()


fun SocketClient.wsUnsubscribe(request: BinanceSocket.Request) = flow<Frame.Text> {
    httpClient.wss(host = url(), path = "/ws") {
        println("Send ${request.method}")

        Json.encodeToString(request)
            .let(Frame::Text)
            .let {
                send(it)
            }

        while (true) {
            if (!isActive) break
            try {
                val othersMessage = incoming.receive() as? Frame.Text
                othersMessage?.let {
                    emit(it)
                }
            } catch (e: Exception) {
                println("WebSocket exception [unsubscribe]: ${e.localizedMessage}")
            }
        }
    }
}.cancellable()