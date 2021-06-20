package ru.touchin.common.byte

object ByteUtils {

    fun ByteArray.toHex(): String {
        return joinToString(separator = "") { byte ->
            "%02x".format(byte)
        }
    }

}
