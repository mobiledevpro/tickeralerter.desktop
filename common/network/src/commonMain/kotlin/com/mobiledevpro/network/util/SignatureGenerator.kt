package com.mobiledevpro.network.util

import com.mobiledevpro.network.BuildKonfig
import java.nio.charset.StandardCharsets
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object SignatureGenerator {

    fun encode(queryMap : Map<String, Any>, secretKey : String) : String {

        //String for API request
        val payload : ByteArray = queryMap.entries.joinToString(separator = "&").toByteArray(StandardCharsets.UTF_8)
        val keySpec = SecretKeySpec(secretKey.toByteArray(StandardCharsets.UTF_8), ALGORITHM)

        //hmac
        return Mac.getInstance(ALGORITHM).let { hmac ->
            hmac.init(keySpec)
            hmac.doFinal(payload).let(::bytesToHex)
        }
    }


    private fun bytesToHex(bytes: ByteArray): String {
        val hexChars = CharArray(bytes.size * 2)
        for (i in bytes.indices) {
            val v = bytes[i].toInt() and 0xFF
            hexChars[i * 2] = "0123456789ABCDEF"[v ushr 4]
            hexChars[i * 2 + 1] = "0123456789ABCDEF"[v and 0x0F]
        }
        return String(hexChars)
    }


    private const val ALGORITHM = "HmacSHA256"

}