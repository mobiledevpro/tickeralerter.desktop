package com.mobiledevpro.network.api

import kotlinx.serialization.Serializable


object BinanceSocket {
    @Serializable
    class Request(
        val method: Method,
        val params: Array<String>,
        val id: Int
    )

    enum class Method {
        SUBSCRIBE,
        UNSUBSCRIBE
    }
}
