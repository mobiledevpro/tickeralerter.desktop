package network.model

import kotlinx.serialization.Serializable

@Serializable
data class ServerTime(
    val serverTime: Long
)
