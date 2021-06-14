package ru.touchin.auth.core.tokens.access.config

import com.auth0.jwt.algorithms.Algorithm
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.touchin.auth.core.tokens.access.properties.AccessTokenProperties
import ru.touchin.common.string.StringUtils.emptyString
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*

@Configuration
class AccessTokenBeanConfig(private val accessTokenProperties: AccessTokenProperties) {

    @Bean
    fun accessTokenSigningAlgorithm(): Algorithm {
        return Algorithm.RSA256(
            accessTokenPublicKey(),
            accessTokenPrivateKey()
        )
    }

    @Bean("accessTokenPublicKey")
    fun accessTokenPublicKey(): RSAPublicKey {
        val keySpecX509 = getKeySpec(accessTokenProperties.keyPair.public, ::X509EncodedKeySpec)

        return keyFactory.generatePublic(keySpecX509) as RSAPublicKey
    }

    @Bean("accessTokenPrivateKey")
    fun accessTokenPrivateKey(): RSAPrivateKey {
        val keySpecPKCS8 = getKeySpec(accessTokenProperties.keyPair.private, ::PKCS8EncodedKeySpec)

        return keyFactory.generatePrivate(keySpecPKCS8) as RSAPrivateKey
    }

    private fun <T> getKeySpec(key: String, keySpecFn: (ByteArray) -> T): T {
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

    companion object {
        val keyFactory: KeyFactory = KeyFactory.getInstance("RSA")
    }

}
