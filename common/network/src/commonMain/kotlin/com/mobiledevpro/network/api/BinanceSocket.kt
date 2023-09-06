package com.mobiledevpro.network.api

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


object BinanceSocket {
    @Serializable
    class Request(
        val method: Method,
        val params: Array<String>,
        val id: Int,
        @Transient
        val listenKey: String? = null
    )

    enum class Method {
        SUBSCRIBE,
        UNSUBSCRIBE,
        REQUEST
    }

    enum class StreamType {
        TICKER,
        KLINE_1H
    }
}
