@file:Suppress("unused")
package ru.touchin.common.random

import ru.touchin.common.string.StringUtils.emptyString
import java.security.SecureRandom

object SecureRandomStringGenerator {

    private val secureRandom = SecureRandom()

    private val DEFAULT_ALLOWED_CHARS = listOf<Iterable<*>>(0..9, 'a'..'z', 'A'..'Z').joinToString(emptyString()) {
        it.joinToString(emptyString())
    }

    fun generate(length: Int, allowedChars: String = DEFAULT_ALLOWED_CHARS): String {
        return buildString(length) {
            repeat(length) {
                val char = secureRandom
                    .nextInt(allowedChars.length)
                    .let(allowedChars::get)

                append(char)
            }
        }
    }

    fun generateRandomBytes(size: Int) = ByteArray(size).also(secureRandom::nextBytes)

}
