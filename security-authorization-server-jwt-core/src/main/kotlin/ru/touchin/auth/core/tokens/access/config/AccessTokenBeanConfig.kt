package ru.touchin.auth.core.tokens.access.config

import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import ru.touchin.auth.core.tokens.access.properties.AccessTokenProperties
import ru.touchin.auth.security.jwt.configurations.JwtConfiguration
import ru.touchin.auth.security.jwt.utils.JwtUtils.getKeySpec
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec

@Configuration
@Import(JwtConfiguration::class)
class AccessTokenBeanConfig(private val accessTokenProperties: AccessTokenProperties) {

    @Bean
    fun accessTokenSigningAlgorithm(
        @Qualifier("accessTokenPublicKey")
        accessTokenPublicKey: RSAPublicKey
    ): Algorithm {
        return Algorithm.RSA256(
            accessTokenPublicKey,
            accessTokenPrivateKey()
        )
    }

    @Bean("accessTokenPrivateKey")
    fun accessTokenPrivateKey(): RSAPrivateKey {
        val keySpecPKCS8 = getKeySpec(accessTokenProperties.keyPair.private, ::PKCS8EncodedKeySpec)

        return keyFactory.generatePrivate(keySpecPKCS8) as RSAPrivateKey
    }

    companion object {
        val keyFactory: KeyFactory = KeyFactory.getInstance("RSA")
    }

}
