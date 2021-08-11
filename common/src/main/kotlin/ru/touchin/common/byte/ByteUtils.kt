package ru.touchin.common.byte

object ByteUtils {

    fun ByteArray.toHex(): String {
        return joinToString(separator = "") { byte ->
            "%02x".format(byte)
        }
    }

    fun String.decodeHex(): ByteArray {
        check(length % 2 == 0) { "Must have an even length" }

        return chunked(2)
            .map { it.toInt(16).toByte() }
            .toByteArray()
    }

}
