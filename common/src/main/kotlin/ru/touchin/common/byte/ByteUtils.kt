package ru.touchin.common.byte

object ByteUtils {

    fun ByteArray.toHex(): String {
        return joinToString(separator = "") { byte ->
            "%02x".format(byte)
        }
    }



    fun decodeHex(hex: String): ByteArray {
        check(hex.length % 2 == 0) { "Must have an even length" }

        return hex.chunked(2)
            .map { it.toInt(16).toByte() }
            .toByteArray()
    }

}
