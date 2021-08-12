package ru.touchin.auth.security.jwt.configurations

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import ru.touchin.auth.security.jwt.properties.AccessTokenPublicProperties
import ru.touchin.auth.security.jwt.utils.JwtUtils.getKeySpec
import java.security.KeyFactory
import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec

@ComponentScan(
    "ru.touchin.auth.security.jwt.http.configurators",
    "ru.touchin.auth.security.jwt.properties",
    "ru.touchin.auth.security.jwks",
    "ru.touchin.auth.security.metadata",
)
@ConfigurationPropertiesScan(
    "ru.touchin.auth.security.jwt.properties",
    "ru.touchin.auth.security.metadata.properties",
)
class JwtConfiguration {

    @Bean("accessTokenPublicKey")
    fun accessTokenPublicKey(
        accessTokenPublicProperties: AccessTokenPublicProperties,
    ): RSAPublicKey {
        val keySpecX509 = getKeySpec(accessTokenPublicProperties.keyPair.public, ::X509EncodedKeySpec)

        return keyFactory.generatePublic(keySpecX509) as RSAPublicKey
    }

    companion object {
        val keyFactory: KeyFactory = KeyFactory.getInstance("RSA")
    }

}
