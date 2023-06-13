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

    fun build(): HttpClient =
        HttpClient(OkHttp) {
            install(WebSockets) {
                this.pingInterval = 5000
            }
        }

    const val TEST_URL = "fstream.binance.com"
}

val jsonFormat = Json { ignoreUnknownKeys = true }

fun HttpClient.wsSubscribe(request: BinanceSocket.Request) = flow<Frame.Text> {
    wss(host = BinanceSocketClientFactory.TEST_URL, path = "/ws") {
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
                println("WebSocket exception [wsSubscribe]: ${e.localizedMessage}")
            }
        }

    }
}.cancellable()


fun HttpClient.wsUnsubscribe(request: BinanceSocket.Request) = flow<Frame.Text> {
    wss(host = BinanceSocketClientFactory.TEST_URL, path = "/ws") {
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