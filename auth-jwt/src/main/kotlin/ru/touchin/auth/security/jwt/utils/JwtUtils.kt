package ru.touchin.auth.security.jwt.utils

import ru.touchin.common.string.StringUtils.emptyString
import java.util.*

object JwtUtils {

   fun <T> getKeySpec(key: String, keySpecFn: (ByteArray) -> T): T {
        val rawKey = getRawKey(key)

        return Base64.getDecoder()
            .decode(rawKey)
            .let(keySpecFn)
    }

    private fun getRawKey(key: String): String {
        return key
            .replace("-----BEGIN .+KEY-----".toRegex(), emptyString())
            .replace("-----END .+KEY-----".toRegex(), emptyString())
            .replace("\n", emptyString())
            .trim()
    }

}
