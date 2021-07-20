package ru.touchin.common.security.hash

import java.nio.charset.StandardCharsets.UTF_8
import java.security.MessageDigest

object HashUtils {

    enum class HashAlgorithm(val code: String) {
        MD5("MD5"),
        SHA1("SHA-1"),
        SHA256("SHA-256"),
    }

    fun String.calculateHash(algorithmName: HashAlgorithm): ByteArray {
        return MessageDigest.getInstance(algorithmName.code)
            .digest(this.toByteArray(UTF_8))
    }

}
