package ru.touchin.common.security.hash

import java.nio.charset.StandardCharsets.UTF_8
import java.security.MessageDigest

object HashUtils {

    enum class HashAlgorithm {
        MD5
    }

    fun String.calculateHash(algorithmName: HashAlgorithm): ByteArray {
        return MessageDigest.getInstance(algorithmName.name)
            .digest(this.toByteArray(UTF_8))
    }

}
